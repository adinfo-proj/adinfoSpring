package com.ad.adinfo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalRestExceptionHandler {

    @ExceptionHandler( value = { RuntimeException.class } )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> internalServerError(Exception e) {
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("msg", e.getMessage());
        return resMap;
    }
}
