package com.github.byrage.springbootbooksearch.api.controller;

import com.github.byrage.springbootbooksearch.api.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(Exception.class)
    public CommonResponse<Void> handleException(Exception e) {
        log.error("{} occurred. message={}", e.getClass().getSimpleName(), e.getMessage(), e);
        return CommonResponse.fail(e.getMessage());
    }
}
