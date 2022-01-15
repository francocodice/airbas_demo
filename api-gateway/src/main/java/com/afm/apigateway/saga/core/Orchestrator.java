package com.afm.apigateway.saga.core;


public abstract class Orchestrator {
    private SagaDefinition sagaDefinition;
    protected abstract SagaDefinition buildSaga(SagaBuilder builder);

    protected SagaExecutor getExecutor() {
        if (sagaDefinition == null)
            sagaDefinition = buildSaga(new SagaBuilder());

        return new SagaExecutor().withSaga(sagaDefinition);
    }
}
