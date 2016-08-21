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
    @Autowired
    private UserRepository userRepository;

    @DirtiesContext
    @Test
    public void testUserRepositorySave()
    {
        LocalDate birthDate = LocalDate.parse("2007-12-03");
        String name = "name";
        String email = "email";
        User expected = new User(name, email, birthDate);
        User actual = userRepository.save(expected);
        Assert.assertEquals(expected, actual);
    }

    @DirtiesContext
    @Test
    public void testUserRepositoryDelete()
    {
        LocalDate birthDate = LocalDate.parse("2007-12-03");
        String name = "name";
        String email = "email";
        User expected = new User(1, name, email, birthDate);
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
