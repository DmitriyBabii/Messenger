package com.example.testtask.events.messages;

import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Message;
import org.springframework.context.ApplicationEvent;

public class CreateMessageEvent extends ApplicationEvent {

    private final Message message;

    public CreateMessageEvent(Eventable source, Message message) {
        super(source);
        this.message = message;
    }

    @Override
    public Eventable getSource() {
        return (Eventable) super.getSource();
    }

    public Message getMessage() {
        return message;
    }
}
