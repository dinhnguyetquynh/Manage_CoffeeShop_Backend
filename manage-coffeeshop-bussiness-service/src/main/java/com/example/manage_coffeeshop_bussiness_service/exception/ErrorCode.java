package com.example.manage_coffeeshop_bussiness_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNAUTHORIZED(1007,"You don't have permission",HttpStatus.FORBIDDEN),
    BILL_DETAIL_EXISTS(1008, "BillDetail already exists", HttpStatus.CONFLICT),
    BILL_DETAIL_NOT_FOUND(1009, "BillDetail not found", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR(1010, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)
            ;

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
    }
