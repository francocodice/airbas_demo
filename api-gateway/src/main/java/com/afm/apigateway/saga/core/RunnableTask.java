package com.afm.apigateway.saga.core;

import org.slf4j.LoggerFactory;

public interface RunnableTask<R, T> {
    R run(T arg) throws SagaException;

    default R executeRun(T arg) throws SagaException {
        try {
            return run(arg);
        } catch (Exception e) {
            LoggerFactory.getLogger(RunnableTask.class).error("Exception running saga transaction: "  + e.getMessage());
            throw new SagaException(e.getMessage(), e);
        }
    }
}

