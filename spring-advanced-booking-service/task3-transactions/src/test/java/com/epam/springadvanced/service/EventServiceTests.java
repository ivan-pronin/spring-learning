package com.epam.springadvanced.service;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.springadvanced.config.SpringConfiguration;
import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Rating;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class EventServiceTests
{
    @Autowired
    private EventService eventService;

    @DirtiesContext
    @Test
    public void testEventServiceCreate()
    {
        LocalDateTime date = LocalDateTime.parse("2007-12-03T10:15:30");
        String name = "name";
        float price = 2.5f;
        Rating rating = Rating.HIGH;
        Event actual = eventService.create(name, date, price, rating);
        Event expected = new Event(name, date, price, rating);
        Assert.assertEquals(expected, actual);
    }
}
