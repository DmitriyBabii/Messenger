package com.example.testtask.models;

public class EventReturns {
    private Object returns;

    public boolean isPresent() {
        return returns != null;
    }

    public Object get() {
        return returns;
    }

    public void set(Object returns) {
        this.returns = returns;
    }
}
