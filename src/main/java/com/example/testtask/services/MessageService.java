package com.example.testtask.services;

import com.example.testtask.models.Account;
import com.example.testtask.models.Message;
import com.example.testtask.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    /*public List<Message> create(int count){
        List<Message> messages = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            messages.add(new Message("Hello", Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()),
                    new Account("Name", ));
        }
        messageRepository.saveAll(messages);
        return messages;
    }*/

    public void save(Message message){
        repository.save(message);
    }
}
