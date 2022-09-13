package com.example.testtask.services;

import com.example.testtask.events.messages.CreateMessageEvent;
import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Message;
import com.example.testtask.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    @EventListener
    public void onApplicationEvent(CreateMessageEvent event) {
        Eventable source = event.getSource();
        source.getEventReturns().set(saveMessage(event.getMessage()));
        source.getEventStatus().set(true);
    }
}
