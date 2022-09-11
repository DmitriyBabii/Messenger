package com.example.testtask.controllers;

import com.example.testtask.beans.HttpSession;
import com.example.testtask.comparators.MessageComparator;
import com.example.testtask.models.Account;
import com.example.testtask.models.Chat;
import com.example.testtask.models.ChatMember;
import com.example.testtask.models.Message;
import com.example.testtask.services.AccountService;
import com.example.testtask.services.ChatService;
import com.example.testtask.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final AccountService accountService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final HttpSession session;

    @Autowired
    public ChatController(AccountService accountService, ChatService chatService, MessageService messageService, HttpSession session) {
        this.accountService = accountService;
        this.chatService = chatService;
        this.messageService = messageService;
        this.session = session;
    }

    /*@GetMapping("/{count}")
    public List<Chat> createAccounts(@PathVariable int count) {
        List<Chat> accounts = chatService.create(count);
        return accounts;
    }*/

    @GetMapping
    public ModelAndView getChats(ModelAndView modelAndView) {
        Account account = accountService.findAccount(session.getPhoneNumber(), session.getPassword());
        List<Chat> chats = chatService.findByAccount(account);
        modelAndView.addObject("inChat", false);
        modelAndView.addObject("chats", chats);
        modelAndView.setViewName("chats.html");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createChat(ModelAndView modelAndView) {
        Chat chat = new Chat();
        ChatMember member = new ChatMember();
        modelAndView.addObject("member", member);
        modelAndView.addObject("chat", chat);
        modelAndView.setViewName("create-chat.html");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveChat(@ModelAttribute Chat chat, @ModelAttribute ChatMember member) {
        if (session.isPresent()) {
            Account creator = accountService.findAccount(session.getPhoneNumber(), session.getPassword());
            Account guest = accountService.findAccount(member.getMemberNumber());
            //Message message = new Message("Text", creator, chat);
            //creator.addMessage(message);
            chat.addAccount(creator);
            chat.addAccount(guest);
            System.out.println(chat.getAccounts());
            chatService.save(chat);
        }
        return new ModelAndView("redirect:/chats");
    }

    @GetMapping("/{id}")
    public ModelAndView getMessages(@PathVariable String id, ModelAndView modelAndView) {
        Account account = accountService.findAccount(session.getPhoneNumber(), session.getPassword());
        if (account != null) {
            List<Chat> chats = chatService.findByAccount(account);
            Optional<Chat> optional = chatService.findById(id);
            Chat chat;
            if (optional.isPresent()) {
                chat = optional.get();
                modelAndView.addObject("chat", chat);
                chat.getMessages().sort(new MessageComparator());
                modelAndView.addObject("messages", chat.getMessages());
            }
            Message message = new Message();
            modelAndView.addObject("account", account);
            modelAndView.addObject("message", message);
            modelAndView.addObject("chats", chats);
            modelAndView.setViewName("chats.html");
        }

        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView saveMessage(@PathVariable String id, @ModelAttribute Message message, ModelAndView modelAndView) {
        Account account = accountService.findAccount(session.getPhoneNumber(), session.getPassword());
        if (account != null) {
            Optional<Chat> optional = chatService.findById(id);
            Chat chat;
            if (optional.isPresent()) {
                chat = optional.get();
                message.setDate(Date.valueOf(LocalDate.now()));
                message.setTime(Time.valueOf(LocalTime.now()));
                message.setChat(chat);
                message.setAccount(account);
                messageService.save(message);
            }
        }

        return new ModelAndView("redirect:/chats/" + id);
    }
}
