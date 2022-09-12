package com.example.testtask.interfaces;

import com.example.testtask.models.EventReturns;
import com.example.testtask.models.EventStatus;

public interface Eventable {
    EventStatus status = new EventStatus();
    EventReturns returns = new EventReturns();

    default EventStatus getEventStatus() {
        return status;
    }

    default EventReturns getEventReturns() {
        return returns;
    }
}
