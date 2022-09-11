package com.example.testtask.services;

import com.example.testtask.models.Account;
import com.example.testtask.models.Chat;
import com.example.testtask.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void save(Chat chat) {
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

}
