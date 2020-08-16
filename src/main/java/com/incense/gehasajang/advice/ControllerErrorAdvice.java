package com.incense.gehasajang.advice;

import com.incense.gehasajang.exception.NotFoundDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundDataException.class)
    public Map<String, Object> handleNotFoundData(NotFoundDataException e) {
        Map<String, Object> contents = new HashMap<>();
        contents.put("status", e.getHttpStatus());
        contents.put("message", e.getMessage());
        return contents;
    }

}
