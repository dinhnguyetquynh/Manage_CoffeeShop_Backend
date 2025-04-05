package com.example.manage_coffeeshop_bussiness_service.service;

import com.example.manage_coffeeshop_bussiness_service.dto.request.AuthenticationRequest;
import com.example.manage_coffeeshop_bussiness_service.dto.request.IntrospectReq;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.AuthenticationRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.EmployeeRes;
import com.example.manage_coffeeshop_bussiness_service.dto.respone.IntrospectRes;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final WebClient webClient;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY ;

    public AuthenticationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8" +
                "080/myapp/api/employee").build();
    }


    //xác thực và tạo token khi đăng nhập
    public AuthenticationRes authenticate(AuthenticationRequest req) {
        EmployeeRes emp = findEmployeeByAccount(req);

        if(emp == null) {
            throw new RuntimeException("Not found employee");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated= passwordEncoder.matches(req.getPassword(), emp.getEmpPassword());


        if(!authenticated){
            throw new RuntimeException("Invalid username or password");
        }

        var token = generateToken(emp);
        return AuthenticationRes.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    public EmployeeRes findEmployeeByAccount(AuthenticationRequest req) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("account",req.getUsername()).build())
                .retrieve()
                .bodyToMono(EmployeeRes.class)
                .block();
    }

    private String generateToken(EmployeeRes emp) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().
                subject(emp.getEmpAccount()).
                issuer("studycoffeeshop.com").
                issueTime(new Date()).
                expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
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






}
