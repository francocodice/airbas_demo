package com.afm.apigateway.saga.core;

public class SagaException extends Exception {
    public SagaException(String message) {
        super(message);
    }

    public SagaException(Throwable e) {
        super(e);
    }

    public SagaException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
