package com.afm.authservice.controller;

import com.afm.authservice.exception.UsernameNotFoundException;
import model.utils.ExceptionResponse;
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
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handlerAllExcptions(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Object> handlerUsernameNotFoundException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


}
