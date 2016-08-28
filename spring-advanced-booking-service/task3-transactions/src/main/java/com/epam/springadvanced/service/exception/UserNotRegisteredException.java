package com.epam.springadvanced.service.exception;

public class UserNotRegisteredException extends Exception
{
    private static final long serialVersionUID = -2968660686113002472L;

    public UserNotRegisteredException()
    {
        super("Sorry, user not found!");
    }
}
