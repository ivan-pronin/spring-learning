package com.epam.springadvanced.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.springadvanced.config.SpringConfiguration;
import com.epam.springadvanced.entity.User;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class UserAccountServiceTests
{
    private static final double DELTA = 0.1;
    private static final int BALANCE_CHANGE = 5;
    private static final int INITIAL_BALANCE = 10;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserBalance()
    {
        Assert.assertEquals(INITIAL_BALANCE, userAccountService.getUserBalance(userService.getById(1)), DELTA);
    }

    @DirtiesContext
    @Test
    public void testIncreaseUserBalance()
    {
        User user = userService.getById(1);
        Double initialBalance = userAccountService.getUserBalance(user);
        userAccountService.increaseBalance(user, BALANCE_CHANGE);
        Assert.assertEquals(userAccountService.getUserBalance(user), initialBalance + BALANCE_CHANGE, DELTA);
    }

    @DirtiesContext
    @Test
    public void testDecreaseUserBalance()
    {
        User user = userService.getById(1);
        Double initialBalance = userAccountService.getUserBalance(user);
        userAccountService.decreaseBalance(user, BALANCE_CHANGE);
        Assert.assertEquals(userAccountService.getUserBalance(user), initialBalance - BALANCE_CHANGE, DELTA);
    }
}
