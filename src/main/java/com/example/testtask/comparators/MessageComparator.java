package com.example.testtask.comparators;


import com.example.testtask.models.entity.Message;

import java.util.Comparator;

public class MessageComparator implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        int result;
        if ((result = o1.getDate().compareTo(o2.getDate())) != 0) {
            return result;
        }
        return o1.getTime().compareTo(o2.getTime());
    }
}
