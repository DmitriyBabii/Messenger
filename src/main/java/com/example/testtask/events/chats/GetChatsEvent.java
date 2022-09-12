package com.example.testtask.events.chats;

import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Account;
import org.springframework.context.ApplicationEvent;

public class GetChatsEvent extends ApplicationEvent {
    private final Account account;

    public GetChatsEvent(Eventable source, Account account) {
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
