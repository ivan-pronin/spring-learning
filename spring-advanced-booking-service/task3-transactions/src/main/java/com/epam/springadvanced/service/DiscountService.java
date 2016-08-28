package com.epam.springadvanced.service;

import java.time.LocalDateTime;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.User;

public interface DiscountService
{
    float getDiscount(User user, Event event, LocalDateTime dateTime);
}
