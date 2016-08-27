package com.epam.springadvanced.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.UserAccountRepository;
import com.epam.springadvanced.service.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService
{
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public double getUserBalance(User user)
    {
        return userAccountRepository.getUserBalance(user);
    }

    @Override
    public void increaseBalance(User user, double amount)
    {
        userAccountRepository.increaseBalance(user, amount);
    }

    @Override
    public void decreaseBalance(User user, double amount)
    {
        userAccountRepository.decreaseBalance(user, amount);
    }
}
