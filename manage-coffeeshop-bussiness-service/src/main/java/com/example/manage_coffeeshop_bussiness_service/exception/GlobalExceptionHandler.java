package com.example.manage_coffeeshop_bussiness_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorPayload> handleAppException(AppException ex) {
        ErrorCode ec = ex.getErrorCode();
        ErrorPayload payload = new ErrorPayload(ec.getCode(), ec.getMessage());
        return ResponseEntity.status(ec.getStatus()).body(payload);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorPayload> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getFieldError().getDefaultMessage();
        ErrorPayload payload = new ErrorPayload(1001, msg);
        return ResponseEntity.badRequest().body(payload);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorPayload> handleRuntime(RuntimeException ex) {
        ErrorPayload payload = new ErrorPayload(1010, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(payload);
    }
}


@Data
@AllArgsConstructor
class ErrorPayload {
    private int code;
    private String message;
}
