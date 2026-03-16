package com.jsb.boilerplate.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ProblemDetail handleBusinessException(CustomException e) {
        log.error("Business Error: {}", e.getMessage());
        ErrorCode errorCode = e.getErrorCode();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                errorCode.getStatus(),
                e.getMessage()
        );

        problemDetail.setTitle("Business Rule Violation");
        problemDetail.setProperty("error_code", errorCode.getCode());
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    /**
     * @Valid 유효성 검사 실패 시 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation Error: {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        
        String detail = bindingResult.getFieldErrors().stream()
                .map(error -> String.format("[%s] %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                detail
        );

        problemDetail.setTitle("Validation Failed");
        problemDetail.setProperty("error_code", ErrorCode.INVALID_INPUT_VALUE.getCode());
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    protected ProblemDetail handleException(Exception e) {
        log.error("Global Exception: ", e);

        return ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage()
        );
    }
}
