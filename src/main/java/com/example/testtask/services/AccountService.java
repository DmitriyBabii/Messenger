package com.example.testtask.services;

import com.example.testtask.events.accounts.CheckLoginAccountEvent;
import com.example.testtask.events.accounts.CreateAccountEvent;
import com.example.testtask.events.accounts.GetAccountByPhoneAndPasswordEvent;
import com.example.testtask.events.accounts.GetAccountByPhoneEvent;
import com.example.testtask.models.entity.Account;
import com.example.testtask.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
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

    @Component
    class CreateAccountListener implements ApplicationListener<CreateAccountEvent> {
        @Override
        public void onApplicationEvent(CreateAccountEvent event) {
            event.getSource()
                    .getEventStatus()
                    .set(saveAccount(event.getAccount()));
        }
    }

    @Component
    class CheckLoginAccountListener implements ApplicationListener<CheckLoginAccountEvent> {
        @Override
        public void onApplicationEvent(CheckLoginAccountEvent event) {
            Account account = findAccount(event.getAccount().getPhoneNumber(), event.getAccount().getPassword());
            event.getSource()
                    .getEventStatus()
                    .set(account != null);
        }
    }

    @Component
    class GetAccountByPhoneAndPasswordListener implements ApplicationListener<GetAccountByPhoneAndPasswordEvent> {
        @Override
        public void onApplicationEvent(GetAccountByPhoneAndPasswordEvent event) {
            Account account = findAccount(event.getPhoneNumber(), event.getPassword());
            boolean isReturn = account != null;
            event.getSource()
                    .getEventStatus()
                    .set(isReturn);
            if (isReturn) {
                event.getSource()
                        .getEventReturns()
                        .set(account);
            }
        }
    }

    @Component
    class GetAccountByPhoneListener implements ApplicationListener<GetAccountByPhoneEvent> {
        @Override
        public void onApplicationEvent(GetAccountByPhoneEvent event) {
            Account account = findAccount(event.getPhoneNumber());
            boolean isReturn = account != null;
            event.getSource()
                    .getEventStatus()
                    .set(isReturn);
            if (isReturn) {
                event.getSource()
                        .getEventReturns()
                        .set(account);
            }
        }
    }
}
