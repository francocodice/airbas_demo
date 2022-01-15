package com.afm.apigateway.saga.core;

import org.slf4j.LoggerFactory;

import java.util.ListIterator;
import java.util.Map;

public class SagaExecutor {
    SagaParamsResolver argumentResolver = new SagaParamsResolver();
    SagaDefinition saga;

    public SagaExecutor withSaga(SagaDefinition saga) {
        this.saga = saga;
        return this;
    }

    public SagaExecutor addParmas(Map<String, Object> args) {
        argumentResolver.update(args);
        return this;
    }

    public SagaExecutor withArg(String name, Object value) {
        argumentResolver.add(name, value);
        return this;
    }

    public SagaExecutor run() throws SagaException, Throwable {
        execute(saga.transactions.listIterator(), saga.transactions.listIterator());
        return this;
    }

    public SagaParamsResolver collect() {
        return argumentResolver;
    }

    private void execute(ListIterator<Transaction> commandsIterator,
                         ListIterator<Transaction> rollbackIterator) throws SagaException, Throwable {
        if (!commandsIterator.hasNext())
            return;

        try {
            Transaction transaction = commandsIterator.next();
            LoggerFactory.getLogger(SagaExecutor.class).info("Executing saga transaction" + transaction);
            transaction
                    .withArgumentResolver(argumentResolver)
                    .run();

            rollbackIterator.next();
            execute(commandsIterator, rollbackIterator);

        } catch (SagaException e){
            if (rollbackIterator.hasPrevious()) {
                LoggerFactory.getLogger(SagaExecutor.class).info("Rolling back saga transaction " + e);
                rollbackIterator.previous().rollback();
                throw e;
            } else {
                LoggerFactory.getLogger(SagaExecutor.class).info("Returning control to main process, exception type " + e.getCause().getClass());
                throw e.getCause();
            }
        }
    }
}
