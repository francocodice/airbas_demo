package com.afm.apigateway.saga.core;

import org.slf4j.LoggerFactory;

public interface RunnableRollback<T> {
    void run(T arg) throws Exception;

    default void executeRun(T arg) throws SagaException {
        try {
            run(arg);
        } catch (Exception e) {
            LoggerFactory.getLogger(RunnableRollback.class).error("Exception running saga transaction", e);
            throw new SagaException(e);
        }
    }
}
