package com.epam.springadvanced.service.exception;

public class EventNotFoundException extends Exception
{
    private static final long serialVersionUID = 7512421474616381204L;

    public EventNotFoundException()
    {
        super("Sorry, event not found!");
    }
}
