package com.epam.springadvanced.repository;

import java.util.Collection;

import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;

public interface TicketRepository
{
    Ticket save(Ticket ticket);

    Collection<Ticket> getAll();

    Collection<Ticket> getByEventName(String eventName);

    Collection<Ticket> getBookedTickets();

    Collection<Ticket> getBookedTicketsByUserId(long userId);

    void saveBookedTicket(User user, Ticket ticket);

    void deleteBookedTicketByUserId(long userId);
}
