package com.afm.apigateway.saga.core;


import java.util.HashMap;
import java.util.Map;

public class SagaParamsResolver {
    private Map<String, Object> parameters = new HashMap<>();

    public void add(String name, Object arg) {
        parameters.put(name, arg);
    }

    public Object getObject(String name) throws SagaException {
        if (!parameters.containsKey(name))
            throw new SagaException("Could not resolve argument placeholder: " + name);

        return parameters.get(name);
    }

    public <T> T get(String name) throws SagaException {
        return (T)getObject(name);
    }

    public void update(Map<String, Object> args) {
        parameters.putAll(args);
    }
}
