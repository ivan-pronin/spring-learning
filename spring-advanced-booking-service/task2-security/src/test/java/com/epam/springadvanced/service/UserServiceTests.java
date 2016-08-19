package com.epam.springadvanced.service;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.springadvanced.config.SpringConfiguration;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class UserServiceTests
{

    @Autowired
    private UserService userService;
    
    @Test
    public void testUserServiceCreate()
    {
        LocalDate birthDate = LocalDate.parse("2007-12-03");
        String name = "name";
        String email = "email";
        User actual = userService.create(name, email, birthDate);
        User expected = new User(name, email, birthDate);
        Assert.assertEquals(expected, actual);
    }
}
