package com.afm.apigateway.controller;

import com.afm.apigateway.saga.core.SagaException;
import com.afm.apigateway.saga.orchestrators.UserCreationOrchestrator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/* Questa classe serve come Handler di tutte le eccezzioni che si verficano nelle API
 * - @ControllerAdvice estendiamo questo handler a tutti i controller di questo modulo
 */

@RestController
@ControllerAdvice
public class ApiGatewayResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(ApiGatewayResponseEntityExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handlerAllExceptions(Exception e, WebRequest request) throws JsonProcessingException {

        int status = Integer.parseInt(StringUtils.substringBefore(e.getMessage()," "));
        HttpStatus httpStatus = HttpStatus.valueOf(status);
        String messageReceived = StringUtils.substringBetween(e.getMessage(), "{", "}");
        String templateJson = "{" + messageReceived + "}";
        Map<String,String> detailExeption =
                new ObjectMapper().readValue(templateJson, HashMap.class);

        logger.info("Found Exeception allowed " + detailExeption);

        return new ResponseEntity<>(detailExeption, httpStatus);
    }

    @ExceptionHandler({SagaException.class})
    public ResponseEntity<Object> handlerSagaExceptions(Exception e, WebRequest request) throws JsonProcessingException {
        logger.info("Found SAGA Exeception " + e.getMessage());
        logger.info("Cause SAGA Exeception " + e.getLocalizedMessage());

        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
