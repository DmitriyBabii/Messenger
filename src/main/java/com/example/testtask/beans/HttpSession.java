package com.example.testtask.beans;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Setter
@Getter
public class HttpSession {
    private String phoneNumber;
    private String password;

    public boolean isPresent() {
        return !(phoneNumber.isEmpty() && password.isEmpty());
    }

    public void clear() {
        phoneNumber = "";  //new String() is redundant 🤫
        password = "";
    }
}

