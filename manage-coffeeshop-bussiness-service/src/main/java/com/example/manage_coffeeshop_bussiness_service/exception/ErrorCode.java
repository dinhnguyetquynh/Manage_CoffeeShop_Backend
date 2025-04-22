package com.example.manage_coffeeshop_bussiness_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNAUTHORIZED(1007,"You don't have permission",HttpStatus.FORBIDDEN)
    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode){
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;

    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;


}
