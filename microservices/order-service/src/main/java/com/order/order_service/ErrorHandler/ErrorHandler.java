package com.order.order_service.ErrorHandler;

import feign.FeignException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
