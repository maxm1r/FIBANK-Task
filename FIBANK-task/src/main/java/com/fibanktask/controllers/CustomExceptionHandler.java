package com.fibanktask.controllers;

import com.fibanktask.dtos.ExceptionDTO;
import com.fibanktask.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionDTO badRequestExceptionHandler(BadRequestException e){
        ExceptionDTO dto = new ExceptionDTO();
        dto.setMsg(e.getMessage());
        dto.setStatus(HttpStatus.BAD_REQUEST.value());
        e.printStackTrace();
        return dto;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionDTO badRequestExceptionHandler(NotFoundException e){
        ExceptionDTO dto = new ExceptionDTO();
        dto.setMsg(e.getMessage());
        dto.setStatus(HttpStatus.NOT_FOUND.value());
        e.printStackTrace();
        return dto;
    }
}