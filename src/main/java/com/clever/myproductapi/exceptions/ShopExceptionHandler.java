package com.clever.myproductapi.exceptions;

import com.clever.myproductapi.responses.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ShopExceptionHandler {
    @ExceptionHandler(value = {CategoryException.class, ProductException.class})
    public ResponseEntity<Object> HandleCategoryException(CategoryException exception, WebRequest webReq){
        ErrorMessage message = new ErrorMessage(new Date(), exception.getMessage());
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> HandleOtherException(Exception exception, WebRequest webReq){
        ErrorMessage message = new ErrorMessage(new Date(), exception.getMessage());
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
