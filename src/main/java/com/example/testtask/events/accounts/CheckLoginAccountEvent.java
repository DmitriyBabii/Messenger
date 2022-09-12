package com.example.testtask.events.accounts;

import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Account;
import org.springframework.context.ApplicationEvent;

public class CheckLoginAccountEvent extends ApplicationEvent {

    private final Account account;

    public CheckLoginAccountEvent(Eventable source, Account account) {
        super(source);
        this.account = account;
    }

    @Override
    public Eventable getSource() {
        return (Eventable) super.getSource();
    }

    public Account getAccount() {
        return account;
    }

}
