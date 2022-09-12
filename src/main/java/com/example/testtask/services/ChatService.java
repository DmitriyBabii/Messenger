package com.example.testtask.services;

import com.example.testtask.events.chats.CreateChatEvent;
import com.example.testtask.events.chats.GetChatByIdEvent;
import com.example.testtask.events.chats.GetChatsEvent;
import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Account;
import com.example.testtask.models.entity.Chat;
import com.example.testtask.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
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

    public void saveChat(Chat chat) {
        repository.save(chat);
    }

    public List<Chat> findByAccount(Account account) {
        return repository.findAll().stream()
                .filter(chat -> chat.getAccounts().stream().anyMatch(acc -> acc.getId().equals(account.getId())))
                .collect(Collectors.toList());
    }

    public Optional<Chat> findById(String id) {
        return repository.findById(id);
    }

    @Component
    public class GetChatsListener implements ApplicationListener<GetChatsEvent> {
        @Override
        public void onApplicationEvent(GetChatsEvent event) {
            List<Chat> chats = findByAccount(event.getAccount());
            Eventable source = event.getSource();
            if (chats != null) {
                source.getEventStatus().set(true);
                source.getEventReturns().set(chats);
            }
        }
    }

    @Component
    public class CreateChatListener implements ApplicationListener<CreateChatEvent> {
        @Override
        public void onApplicationEvent(CreateChatEvent event) {
            saveChat(event.getChat());
            event.getSource()
                    .getEventStatus()
                    .set(true);
        }
    }

    @Component
    public class GetChatByIdListener implements ApplicationListener<GetChatByIdEvent> {
        @Override
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
}
