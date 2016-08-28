package com.epam.springadvanced.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class PasswordEncoderTests
{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncodePassword()
    {
        String password = "123456";
        String encodedPassword = "$2a$10$qFoXUuO6Exs2xtYY/EVZ7.gjF8U/ilSsxJCH3n2N33hUTINTeYbSm";
        Assert.assertTrue(passwordEncoder.matches(password, encodedPassword));
    }

    @Test
    public void testEncodePasswordAdmin()
    {
        String password = "admin";
        String encodedPassword = "$2a$10$xotv7AU7aIiU7/aKj3V6TOOxAWdO7Ug1Lw4x6HK2OGyAEauaq.ROG";
        Assert.assertTrue(passwordEncoder.matches(password, encodedPassword));
    }
}
