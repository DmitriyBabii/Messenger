package com.example.testtask.services;

import com.example.testtask.models.Account;
import com.example.testtask.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public boolean saveAccount(Account account) {
        try {
            repository.save(account);
            return true;
        } catch (DataIntegrityViolationException ex) {
            ex.printStackTrace();
            System.out.println("This account already exist!");
            System.out.println("AccountService.saveAccount()");
            return false;
        }
    }

    public Account findAccount(String phoneNumber, String password) {
        return repository.findByNameAndPhone(phoneNumber, password);
    }

    public Account findAccount(String phoneNumber) {
        return repository.findByPhone(phoneNumber);
    }

}
