package com.epam.springadvanced.service.exception;

public class EventNotFoundException extends Exception{
    public EventNotFoundException() {
        super("Sorry, event not found!");
    }
}
