package com.epam.springadvanced.repository;

import java.time.LocalDate;

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
public class UserRepositoryTests
{
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final LocalDate BIRTHDATE = LocalDate.parse("2007-12-03");

    @Autowired
    private UserRepository userRepository;

    @DirtiesContext
    @Test
    public void testUserRepositorySave()
    {
        User expected = new User(NAME, EMAIL, BIRTHDATE);
        expected.setPassword(PASSWORD);
        User actual = userRepository.save(expected);
        actual.setPassword(PASSWORD);
        Assert.assertEquals(expected, actual);
    }

    @DirtiesContext
    @Test
    public void testUserRepositoryDelete()
    {
        User expected = new User(1, NAME, EMAIL, BIRTHDATE);
        expected.setPassword(PASSWORD);
        userRepository.save(expected);
        int size = userRepository.getAll().size();
        userRepository.delete(1);
        Assert.assertEquals(size - 1, userRepository.getAll().size());
    }

    @Test
    public void testUserRepositoryGetAll()
    {
        Assert.assertEquals(4, userRepository.getAll().size());
    }
}
