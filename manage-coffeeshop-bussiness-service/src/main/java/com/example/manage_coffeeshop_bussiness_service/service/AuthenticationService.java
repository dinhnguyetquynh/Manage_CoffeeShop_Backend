package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.AuthenticationRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.request.IntrospectReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.AuthenticationRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.IntrospectRes;
import com.example.manage_coffeeshop_bussiness_service.enums.Role;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final WebClient webClient;
    @Autowired
    private RedisService redisService;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY ;

//    public AuthenticationService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/employee").build();
//    }
    public AuthenticationService(WebClient.Builder webClientBuilder,
                       @Value("${dataservice.base-url}") String baseUrl){
        this.webClient = webClientBuilder.baseUrl(baseUrl+"/api/employee").build();
    }


    //xác thực và tạo token khi đăng nhập
    public AuthenticationRes authenticate(AuthenticationRequest req, HttpServletResponse response) {
        EmployeeRes emp = findEmployeeByAccount(req);

        if(emp == null) {
            throw new RuntimeException("Not found employee");
        }
        System.out.println("Employee tim dc la:"+emp.getEmpAccount());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated= passwordEncoder.matches(req.getPassword(), emp.getEmpPassword());


        if(!authenticated){
            throw new RuntimeException("Invalid username or password");
        }


        String accessToken = generateToken(emp, 1); // 1 hour
        String refreshToken = generateToken(emp, 7 * 24);// 7 days

        redisService.saveRefreshToken(refreshToken,7 * 24 * 60 * 60);

        // Set refresh token in HttpOnly cookie
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // true nếu dùng HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
        response.addCookie(cookie);

        return AuthenticationRes.builder()
                .authenticated(true)
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    public EmployeeRes findEmployeeByAccount(AuthenticationRequest req) {
        System.out.println("Request is: " + req.getUsername());

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("account", req.getUsername()).build())
                .retrieve()
                .bodyToMono(EmployeeRes.class)
                .retryWhen(
                        // Retry 3 lần, mỗi lần cách 2 giây
                        Retry.fixedDelay(7, Duration.ofSeconds(2))
                                // Lọc loại lỗi muốn retry, chỉ retry nếu là lỗi hệ thống
                                .filter(ex -> ex instanceof RuntimeException)
                )
                .block();
    }


    public String generateToken(EmployeeRes emp,int hours) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().
                subject(emp.getEmpAccount()).
                issuer("studycoffeeshop.com").
                claim("employeeId",emp.getEmpId()).
                issueTime(new Date()).
                expirationTime(new Date(
                        Instant.now().plus(hours, ChronoUnit.HOURS).toEpochMilli()
                )).
                claim("scope",emp.getEmpRole()).
                build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can not create token",e);
            throw new RuntimeException(e);
        }

    }

    public IntrospectRes introspect(IntrospectReq req) throws JOSEException, ParseException {
        var token = req.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectRes.builder().
                valid(verified && expirationDate.after(new Date())).
                build();

    }

    //Lấy RT từ cookie , sau đó tạo AT mới
    public String extractRefreshTokenFromToken(HttpServletRequest request) throws ParseException, JOSEException {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if("refreshToken".equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;

    }

    public AuthenticationRes generateNewAccessToken(String refreshToken) throws JOSEException, ParseException {

            SignedJWT jwt = SignedJWT.parse(refreshToken);
            boolean valid = jwt.verify(new MACVerifier(SIGNER_KEY.getBytes()));

            if (!valid || jwt.getJWTClaimsSet().getExpirationTime().before(new Date())) {
                throw new RuntimeException("Refreshtoken not valid");
            }

            String username = jwt.getJWTClaimsSet().getSubject();
            String role = (String) jwt.getJWTClaimsSet().getClaim("scope");

            EmployeeRes emp = new EmployeeRes();
            emp.setEmpAccount(username);
            emp.setEmpRole(Role.valueOf(role));

            String newAccessToken =generateToken(emp,1); // 1 hour
            return AuthenticationRes.builder()
                    .authenticated(true)
                    .token(newAccessToken)
                    .build();



    }
    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = extractTokenFromCookie(request);
        if (refreshToken != null && redisService.exists(refreshToken)) {
            redisService.deleteRefreshToken(refreshToken);
        }

        // Xoá cookie phía client
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("Đăng xuất thành công");
    }





}
