package com.epam.springadvanced.service;

import com.epam.springadvanced.entity.User;

public interface UserAccountService
{
    double getUserBalance(User user);

    void increaseBalance(User user, double amount);

    void decreaseBalance(User user, double amount);
}
