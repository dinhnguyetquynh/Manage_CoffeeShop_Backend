package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.AuthenticationRequest;

import com.example.manage_coffeeshop_bussiness_service.dto.respone.AuthenticationRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.CustomerRes;

import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import com.example.manage_coffeeshop_bussiness_service.enums.Role;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.NonFinal;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;

@Service
public class AuthenticationServiceCus {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final WebClient webClient;
    @Autowired
    private RedisService redisService;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY ;

//    public AuthenticationServiceCus(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/myapp/api/customers").build();
//    }
    public AuthenticationServiceCus(WebClient.Builder webClientBuilder,
                                 @Value("${dataservice.base-url}") String baseUrl){
        this.webClient = webClientBuilder.baseUrl(baseUrl+"/api/customers").build();
    }


    public AuthenticationRes authenticate(AuthenticationRequest req, HttpServletResponse response) {
        CustomerRes customer = findCustomerByAccount(req);

        if(customer == null) {
            throw new RuntimeException("Not found customer");
        }
        System.out.println("customer tim dc la:"+customer.getAccountCus());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated= passwordEncoder.matches(req.getPassword(), customer.getPasswordCus());


        if(!authenticated){
            throw new RuntimeException("Invalid username or password");
        }


        String accessToken = generateToken(customer, 1); // 1 hour
        String refreshToken = generateToken(customer, 7 * 24);// 7 days

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
    public CustomerRes findCustomerByAccount(AuthenticationRequest req) {
        System.out.println("Request is: " + req.getUsername());
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/account")
                        .queryParam("account", req.getUsername()).build())
                .retrieve()
                .bodyToMono(CustomerRes.class)
                .block();
    }
    public String generateToken(CustomerRes cus, int hours) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().
                subject(cus.getAccountCus()).
                issuer("studycoffeeshop.com").
                claim("customerId",cus.getCustomerId()).
                issueTime(new Date()).
                expirationTime(new Date(
                        Instant.now().plus(hours, ChronoUnit.HOURS).toEpochMilli()
                )).
                claim("scope", Role.CUSTOMER).
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
    public String extractCustomerIdFromToken(String token) {
        try {
            // 1. Parse token
            JWSObject jwsObject = JWSObject.parse(token);

            // 2. Verify signature
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            if (!jwsObject.verify(verifier)) {
                throw new RuntimeException("Invalid token signature");
            }

            // 3. Get claims
            Map<String, Object> jsonPayload = jwsObject.getPayload().toJSONObject();
            String customerId = String.valueOf(jsonPayload.get("customerId"));

            return customerId;

        } catch (Exception e) {
            log.error("Failed to parse token", e);
            throw new RuntimeException("Invalid token", e);
        }
    }

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




}
