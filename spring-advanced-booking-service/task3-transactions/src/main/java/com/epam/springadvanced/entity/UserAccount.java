package com.epam.springadvanced.entity;

public class UserAccount
{
    private User user;
    private double balance;

    public UserAccount(User user, double balance)
    {
        this.user = user;
        this.balance = balance;
    }

    public User getUser()
    {
        return user;
    }

    public double getBalance()
    {
        return balance;
    }

}
