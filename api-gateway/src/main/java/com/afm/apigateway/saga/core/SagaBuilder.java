package com.afm.apigateway.saga.core;


import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SagaBuilder {
    private SagaDefinition saga = new SagaDefinition();

    public interface Collector<T> {
        T collect() throws SagaException;
    }

    @Setter
    @RequiredArgsConstructor
    public static class SagaTransaction<R, T> implements Transaction {
        private SagaParamsResolver resolver;
        private Collector<T> argumentCollector = () -> null;
        private R result;
        private String resultPlaceholder;
        private RunnableTask<R, T> action;
        private RunnableRollback<R> rollback = (a) -> {};
        private Logger logger = LoggerFactory.getLogger(SagaTransaction.class);
        private final SagaBuilder builder;

        @Override
        public Transaction withArgumentResolver(SagaParamsResolver argumentResolver) {
            this.resolver = argumentResolver;
            return this;
        }

        @Override
        public void run() throws SagaException {
            result = action.executeRun(argumentCollector.collect());

            if (resultPlaceholder != null) {
                LoggerFactory.getLogger(SagaTransaction.class).info("Saving result to " + resultPlaceholder);
                resolver.add(resultPlaceholder, result);
            }
        }

        @Override
        public void rollback() throws SagaException {
            rollback.executeRun(result);
        }

        public SagaTransaction<R, T> addParam(String param) {
            this.argumentCollector = () -> {
                LoggerFactory.getLogger(SagaTransaction.class).info("Resolving argument " + param + " from resolver: " + resolver.get(param));
                return resolver.get(param);
            };
            return this;
        }

        public SagaTransaction<R, T> saveTo(String resultPlaceholder) {
            this.resultPlaceholder = resultPlaceholder;
            return this;
        }

        public SagaTransaction<R, T> withCompensation(RunnableRollback<R> compensation) {
            this.rollback = compensation;
            return this;
        }

        public SagaBuilder step() {
            return builder.addTransaction(this);
        }

        public SagaDefinition build() {
            return builder.addTransaction(this).build();
        }
    }

    public <R, T> SagaBuilder addTransaction(SagaBuilder.SagaTransaction<R, T> transaction) {
        saga.add(transaction);
        return this;
    }

    public <R,T> SagaBuilder.SagaTransaction<R,T> invoke(RunnableTask<R,T> action) {
        SagaBuilder.SagaTransaction<R, T> transaction = new SagaBuilder.SagaTransaction<>(this);
        transaction.setAction(action);
        return transaction;
    }


    public SagaDefinition build() {
        return saga;
    }
}
