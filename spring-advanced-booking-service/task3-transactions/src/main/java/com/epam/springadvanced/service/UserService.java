package com.epam.springadvanced.service;

import java.time.LocalDate;
import java.util.Collection;

import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;

public interface UserService
{
    User create(String name, String email, LocalDate birthDay);

    User register(User user);

    void remove(User user);

    User getById(long id);

    User getUserByEmail(String email);

    User getUserByName(String name);

    Collection<Ticket> getBookedTickets();

    Collection<Ticket> getBookedTicketsByUserId(long userId);
}
