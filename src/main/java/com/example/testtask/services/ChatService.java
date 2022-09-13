package com.example.testtask.services;

import com.example.testtask.events.chats.CreateChatEvent;
import com.example.testtask.events.chats.GetChatByIdEvent;
import com.example.testtask.events.chats.GetChatsEvent;
import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Account;
import com.example.testtask.models.entity.Chat;
import com.example.testtask.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository repository;

    @Autowired
    public ChatService(ChatRepository repository) {
        this.repository = repository;
    }

    public Chat saveChat(Chat chat) {
        return repository.save(chat);
    }

    public List<Chat> findByAccount(Account account) {
        return repository.findAll().stream()
                .filter(chat -> chat.getAccounts().stream().anyMatch(acc -> acc.getId().equals(account.getId())))
                .collect(Collectors.toList());
    }

    public Optional<Chat> findById(String id) {
        return repository.findById(id);
    }


    @EventListener
    public void onApplicationEvent(GetChatsEvent event) {
        List<Chat> chats = findByAccount(event.getAccount());
        Eventable source = event.getSource();
        if (chats != null) {
            source.getEventStatus().set(true);
            source.getEventReturns().set(chats);
        }
    }

    @EventListener
    public void onApplicationEvent(CreateChatEvent event) {
        Eventable source = event.getSource();
        source.getEventReturns().set(saveChat(event.getChat()));
        source.getEventStatus().set(true);
    }

    @EventListener
    public void onApplicationEvent(GetChatByIdEvent event) {
        Optional<Chat> chat = findById(event.getId());
        Eventable source = event.getSource();
        chat.ifPresent(
                it -> {
                    source.getEventStatus().set(true);
                    source.getEventReturns().set(it);
                }
        );
    }
}
