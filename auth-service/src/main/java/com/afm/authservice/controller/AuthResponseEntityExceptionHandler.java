package com.afm.authservice.controller;

import model.exception.BadRequestException;
import model.exception.ResourceNotFoundException;
import model.utils.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/* questa classe serve come Handler di tutte le eccezzioni che si verficano nelle API
 * - @ControllerAdvice estendiamo questo handler a tutti i controller di questo modulo
 */

@RestController
@ControllerAdvice
public class AuthResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(AuthResponseEntityExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handlerAllExceptions(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
        logger.error("Exception Not managed found : " + e.getMessage() + " with cause: " +  e.getCause());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handlerBadRequestException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handlerResourceNotFoundException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
        System.out.println("ResourceNotFoundException EXCEPTION FIND");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }




}
