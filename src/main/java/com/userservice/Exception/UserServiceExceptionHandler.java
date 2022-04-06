package com.userservice.Exception;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.userservice.Model.ApiError;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
class UserServiceExceptionHandler extends ResponseEntityExceptionHandler {





    @ExceptionHandler({UserNotFoundException.class,
            EmailAlreadyExistsException.class,UserIdExistsException.class})
    ResponseEntity customerNotFoundHandler(Exception exception, ServletWebRequest request){
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setCode("404");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {



        ApiError apiError = new ApiError();
        apiError.setCode("400");
        if(ex.getLocalizedMessage().contains("BloodGroup"))
            apiError.setMessage("Blood Group is Enum type and cannot have Dynamic values");
        else{
            apiError.setMessage("Gender is Enum type and cannot have Dynamic values");
        }

//        apiError.setMessage(ex.getLocalizedMessage().substring(73,79)+ " cannot have dynamic values");

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }











    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {


        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();


        List<String> errors = fieldErrors.stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage())
                .collect(Collectors.toList());


        ApiError apiError = new ApiError();
        apiError.setCode("400");
        apiError.setMessage(String.valueOf(errors));

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


}
