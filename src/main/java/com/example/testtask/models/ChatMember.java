package com.example.testtask.models;

import lombok.ToString;

@ToString
public class ChatMember {
    private String memberNumber;

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public String getMemberNumber() {
        return memberNumber;
    }
}
