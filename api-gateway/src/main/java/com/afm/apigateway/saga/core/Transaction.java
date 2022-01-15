package com.afm.apigateway.saga.core;



public interface Transaction {
    Transaction withArgumentResolver(SagaParamsResolver argumentResolver);
    void run() throws SagaException;
    void rollback() throws SagaException;
}
