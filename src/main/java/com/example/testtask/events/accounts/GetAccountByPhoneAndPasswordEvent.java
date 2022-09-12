package com.example.testtask.events.accounts;

import com.example.testtask.interfaces.Eventable;
import org.springframework.context.ApplicationEvent;

public class GetAccountByPhoneAndPasswordEvent extends ApplicationEvent {

    private final String phoneNumber;
    private final String password;

    public GetAccountByPhoneAndPasswordEvent(Eventable source, String phoneNumber, String password) {
        super(source);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Override
    public Eventable getSource() {
        return (Eventable) source;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
