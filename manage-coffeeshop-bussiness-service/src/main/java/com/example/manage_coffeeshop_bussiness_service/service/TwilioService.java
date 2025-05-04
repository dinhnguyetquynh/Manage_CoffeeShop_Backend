//package com.example.manage_coffeeshop_bussiness_service.service;
//
//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//
//import com.twilio.type.PhoneNumber;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class TwilioService {
//
//    @Value("${twilio.accountSid}")
//    private String accountSid;
//
//    @Value("${twilio.authToken}")
//    private String authToken;
//
//    @Value("${twilio.phoneNumber}")
//    private String fromPhone;
//
//    @PostConstruct
//    public void init() {
//        Twilio.init(accountSid, authToken);
//    }
//
//    public void sendOtp(String toPhone, String otp) {
//        Message.creator(
//                new PhoneNumber(toPhone),
//                new PhoneNumber(fromPhone),
//                "Your OTP code is: " + otp
//        ).create();
//    }
//}
