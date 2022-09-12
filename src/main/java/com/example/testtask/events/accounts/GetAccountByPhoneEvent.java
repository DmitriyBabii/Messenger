package com.example.testtask.events.accounts;

import com.example.testtask.interfaces.Eventable;
import org.springframework.context.ApplicationEvent;

public class GetAccountByPhoneEvent extends ApplicationEvent {

    private final String phoneNumber;

    public GetAccountByPhoneEvent(Eventable source, String phoneNumber) {
        super(source);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Eventable getSource() {
        return (Eventable) source;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
