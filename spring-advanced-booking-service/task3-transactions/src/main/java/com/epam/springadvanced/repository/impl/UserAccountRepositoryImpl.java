package com.epam.springadvanced.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.UserAccountRepository;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public double getUserBalance(User user)
    {
        Long userId = user.getId();
        if (userId != null)
        {
            return jdbcTemplate.queryForObject("SELECT balance from user_account where user_id = ?", Double.class,
                    userId);
        }
        return 0;
    }

    @Override
    public void increaseBalance(User user, double amount)
    {
        double initialBalance = getUserBalance(user);
        setUserBalance(user, initialBalance + amount);
    }

    @Override
    public void decreaseBalance(User user, double amount)
    {
        double initialBalance = getUserBalance(user);
        setUserBalance(user, initialBalance - amount);
    }

    private void setUserBalance(User user, double balance)
    {
        jdbcTemplate.update("UPDATE user_account set balance = ? where user_id = ?", balance, user.getId());
    }
}
