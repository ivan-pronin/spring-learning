package com.epam.springadvanced.repository;

import com.epam.springadvanced.entity.User;

public interface UserAccountRepository
{
    double getUserBalance(User user);

    void increaseBalance(User user, double amount);

    void decreaseBalance(User user, double amount);
}
