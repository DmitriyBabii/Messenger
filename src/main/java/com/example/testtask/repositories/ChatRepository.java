package com.example.testtask.repositories;

import com.example.testtask.models.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends CrudRepository<Chat, String> {

    List<Chat> findAll();

    Optional<Chat> findById(String id);
}
