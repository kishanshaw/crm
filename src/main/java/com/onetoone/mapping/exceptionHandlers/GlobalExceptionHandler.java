package com.onetoone.mapping.exceptionHandlers;

import com.onetoone.mapping.dtos.ExceptionResponse;
import com.onetoone.mapping.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ExceptionResponse userNotFoundExceptionHandler(HttpServletRequest request, UserNotFoundException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .statusCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .timestamp(new Date().toString())
                .uri(request.getRequestURI())
                .build();
    }
}
