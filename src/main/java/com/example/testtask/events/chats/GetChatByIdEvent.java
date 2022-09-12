package com.example.testtask.events.chats;

import com.example.testtask.interfaces.Eventable;
import org.springframework.context.ApplicationEvent;

public class GetChatByIdEvent extends ApplicationEvent {
    private final String id;

    public GetChatByIdEvent(Eventable source, String id) {
        super(source);
        this.id = id;
    }

    @Override
    public Eventable getSource() {
        return (Eventable) super.getSource();
    }

    public String getId() {
        return id;
    }
}
