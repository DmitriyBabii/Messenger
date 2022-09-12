package com.example.testtask.controllers;

import com.example.testtask.beans.HttpSession;
import com.example.testtask.events.accounts.CheckLoginAccountEvent;
import com.example.testtask.events.accounts.CreateAccountEvent;
import com.example.testtask.events.accounts.GetAccountByPhoneAndPasswordEvent;
import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/account")
public class AccountController implements Eventable {

    private final HttpSession session;
    private final ApplicationEventPublisher aep;

    @Autowired
    public AccountController(HttpSession session, ApplicationEventPublisher aep) {
        this.session = session;
        this.aep = aep;
    }

    @GetMapping
    public ModelAndView getAccount(ModelAndView modelAndView) {
        aep.publishEvent(new GetAccountByPhoneAndPasswordEvent(this, session.getPhoneNumber(), session.getPassword()));
        if (status.get() && returns.isPresent()) {
            modelAndView.addObject("account", returns.get());
            modelAndView.setViewName("account.html");
            return modelAndView;
        }
        return new ModelAndView("redirect:/account/login");
    }

    @PostMapping
    public ModelAndView exit(ModelAndView modelAndView) {
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
        aep.publishEvent(new CreateAccountEvent(this, account));
        if (status.get()) {
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
        aep.publishEvent(new CheckLoginAccountEvent(this, account));
        if (status.get()) {
            session.setPhoneNumber(account.getPhoneNumber());
            session.setPassword(account.getPassword());
            return new ModelAndView("redirect:/account");
        }
        return login(modelAndView);
    }

}
