package com.example.testtask.models;

public class EventStatus {
    private boolean status;

    public boolean get() {
        if (status) {
            status = false;
            return true;
        }
        return false;
    }

    public void set(boolean status) {
        this.status = status;
    }
}
