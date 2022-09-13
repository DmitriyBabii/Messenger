package com.example.testtask.controllers;

import com.example.testtask.beans.HttpSession;
import com.example.testtask.comparators.MessageComparator;
import com.example.testtask.events.accounts.GetAccountByPhoneAndPasswordEvent;
import com.example.testtask.events.accounts.GetAccountByPhoneEvent;
import com.example.testtask.events.chats.CreateChatEvent;
import com.example.testtask.events.chats.GetChatByIdEvent;
import com.example.testtask.events.chats.GetChatsEvent;
import com.example.testtask.events.messages.CreateMessageEvent;
import com.example.testtask.interfaces.Eventable;
import com.example.testtask.models.entity.Account;
import com.example.testtask.models.entity.Chat;
import com.example.testtask.models.ChatMember;
import com.example.testtask.models.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController implements Eventable {

    private final HttpSession session;
    private final ApplicationEventPublisher aep;

    @Autowired
    public ChatController(HttpSession session, ApplicationEventPublisher aep) {
        this.session = session;
        this.aep = aep;
    }

    @GetMapping
    public ModelAndView getChats(ModelAndView modelAndView) {
        aep.publishEvent(new GetAccountByPhoneAndPasswordEvent(this, session.getPhoneNumber(), session.getPassword()));
        if (status.get() && returns.isPresent()) {
            Account account = (Account) returns.get();
            aep.publishEvent(new GetChatsEvent(this, account));
        }
        if (status.get() && returns.isPresent()) {
            @SuppressWarnings("unchecked")
            List<Chat> chats = (List<Chat>) returns.get();
            modelAndView.addObject("chats", chats);
            modelAndView.setViewName("chats.html");
        }
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createChat(ModelAndView modelAndView) {
        aep.publishEvent(new GetAccountByPhoneAndPasswordEvent(this, session.getPhoneNumber(), session.getPassword()));
        if (status.get() && returns.isPresent()) {
            Chat chat = new Chat();
            ChatMember member = new ChatMember();
            modelAndView.addObject("member", member);
            modelAndView.addObject("chat", chat);
            modelAndView.setViewName("create-chat.html");
            return modelAndView;
        }
        return new ModelAndView("redirect:/chats");
    }

    @PostMapping("/create")
    public ModelAndView saveChat(@ModelAttribute Chat chat, @ModelAttribute ChatMember member, ModelAndView modelAndView) {
        aep.publishEvent(new GetAccountByPhoneAndPasswordEvent(this, session.getPhoneNumber(), session.getPassword()));
        if (status.get() && returns.isPresent()) {
            Account creator = (Account) returns.get();

            Account guest;
            aep.publishEvent(new GetAccountByPhoneEvent(this, member.getMemberNumber()));
            if (status.get() && returns.isPresent()) {
                guest = (Account) returns.get();
            } else {
                //invalid data, try again
                return createChat(modelAndView);
            }

            chat.addAccount(creator);
            chat.addAccount(guest);
            aep.publishEvent(new CreateChatEvent(this, chat));
            if (status.get()) {
                System.out.println("Chat " + chat.getName() + " was create!");
            } else {
                return createChat(modelAndView);
            }
        }
        return new ModelAndView("redirect:/chats");
    }

    @GetMapping("/{id}")
    public ModelAndView getMessages(@PathVariable String id, ModelAndView modelAndView) {
        aep.publishEvent(new GetAccountByPhoneAndPasswordEvent(this, session.getPhoneNumber(), session.getPassword()));

        //check account
        Account account = null;
        if (status.get() && returns.isPresent()) {
            account = (Account) returns.get();
            aep.publishEvent(new GetChatsEvent(this, account));
        }
        //get chats if account is present
        if (status.get() && returns.isPresent()) {
            @SuppressWarnings("unchecked")
            List<Chat> chats = (List<Chat>) returns.get();
            modelAndView.addObject("chats", chats);
            aep.publishEvent(new GetChatByIdEvent(this, id));
        }
        //get chat if chats is present
        if (status.get() && returns.isPresent()) {
            Chat chat = (Chat) returns.get();
            modelAndView.addObject("chat", chat);
            chat.getMessages().sort(new MessageComparator());
            modelAndView.addObject("messages", chat.getMessages());
        }
        Message message = new Message();
        modelAndView.addObject("account", account);
        modelAndView.addObject("message", message);
        modelAndView.setViewName("chats.html");
        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView saveMessage(@PathVariable String id, @ModelAttribute Message message, ModelAndView modelAndView) {
        aep.publishEvent(new GetAccountByPhoneAndPasswordEvent(this, session.getPhoneNumber(), session.getPassword()));
        //check account
        if (status.get() && returns.isPresent()) {
            Account account = (Account) returns.get();
            message.setAccount(account);
            aep.publishEvent(new GetChatByIdEvent(this, id));
        }
        //if chat present
        if (status.get() && returns.isPresent()) {
            Chat chat = (Chat) returns.get();
            message.setChat(chat);
            message.setDate(Date.valueOf(LocalDate.now()));
            message.setTime(Time.valueOf(LocalTime.now()));
            aep.publishEvent(new CreateMessageEvent(this, message));
        }
        return new ModelAndView("redirect:/chats/" + id);
    }
}
