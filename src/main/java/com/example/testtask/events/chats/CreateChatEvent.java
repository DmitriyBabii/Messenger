package com.example.testtask.events.chats;

import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Chat;
import org.springframework.context.ApplicationEvent;

public class CreateChatEvent extends ApplicationEvent {
    private final Chat chat;

    public CreateChatEvent(Eventable source, Chat chat) {
        super(source);
        this.chat = chat;
    }

    @Override
    public Eventable getSource() {
        return (Eventable) super.getSource();
    }

    public Chat getChat() {
        return chat;
    }
}
