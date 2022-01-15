package com.afm.apigateway.saga.core;


import java.util.LinkedList;

public class SagaDefinition {
    LinkedList<Transaction> transactions = new LinkedList<>();
    public void add(Transaction transaction) {
        transactions.add(transaction);
    }
}
