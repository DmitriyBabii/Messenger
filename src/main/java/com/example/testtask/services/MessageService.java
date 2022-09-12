package com.example.testtask.services;

import com.example.testtask.events.messages.CreateMessageEvent;
import com.example.testtask.models.entity.Message;
import com.example.testtask.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public void saveMessage(Message message) {
        repository.save(message);
    }

    @Component
    public class CreateMessageListener implements ApplicationListener<CreateMessageEvent> {

        @Override
        public void onApplicationEvent(CreateMessageEvent event) {
            saveMessage(event.getMessage());
            event.getSource().getEventStatus().set(true);
        }
    }
}
