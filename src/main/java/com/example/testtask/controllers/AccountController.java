package com.example.testtask.controllers;

import com.example.testtask.beans.HttpSession;
import com.example.testtask.models.Account;
import com.example.testtask.models.Message;
import com.example.testtask.services.AccountService;
import com.example.testtask.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final MessageService messageService;
    private final HttpSession session;

    @Autowired
    public AccountController(AccountService accountService, MessageService messageService, HttpSession session) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.session = session;
    }

    @GetMapping
    public ModelAndView getAccount(ModelAndView modelAndView) {
        if (session == null) {
            return new ModelAndView("redirect:/account/login");
        }
        Account account = accountService.findAccount(session.getPhoneNumber(), session.getPassword());
        if (account != null) {
            modelAndView.addObject("account", account);
            modelAndView.setViewName("account.html");
            return modelAndView;
        }
        return new ModelAndView("redirect:/account/login");
    }

    @PostMapping
    public ModelAndView exit(ModelAndView modelAndView) {
        Account account = new Account();
        session.clear();
        return new ModelAndView("redirect:/account/login");
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        Account account = new Account();
        modelAndView.addObject("account", account);
        modelAndView.setViewName("registration.html");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView saveAccount(@ModelAttribute Account account, ModelAndView modelAndView) {
        if (accountService.saveAccount(account)) {
            session.setPhoneNumber(account.getPhoneNumber());
            session.setPassword(account.getPassword());
            return new ModelAndView("redirect:/account");
        }
        return register(modelAndView);
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        Account account = new Account();
        modelAndView.addObject("account", account);
        modelAndView.setViewName("login.html");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView getAccount(@ModelAttribute Account account, ModelAndView modelAndView) {
        if (session == null) {
            return new ModelAndView("redirect:/account");
        }
        Account accountSQL = accountService.findAccount(account.getPhoneNumber(), account.getPassword());
        if (accountSQL != null) {
            session.setPhoneNumber(accountSQL.getPhoneNumber());
            session.setPassword(accountSQL.getPassword());
            return new ModelAndView("redirect:/account");
        }
        return login(modelAndView);
    }

}
