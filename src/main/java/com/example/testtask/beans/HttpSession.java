package com.example.testtask.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Scope("singleton")
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

