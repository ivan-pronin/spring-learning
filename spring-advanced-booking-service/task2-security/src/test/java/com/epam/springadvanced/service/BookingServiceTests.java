package com.epam.springadvanced.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.springadvanced.config.SpringConfiguration;
import com.epam.springadvanced.config.web.SecurityConfiguration;
import com.epam.springadvanced.entity.Auditorium;
import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Seat;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.AuditoriumRepository;
import com.epam.springadvanced.repository.EventRepository;
import com.epam.springadvanced.service.exception.TicketAlreadyBookedException;
import com.epam.springadvanced.service.exception.TicketWithoutEventException;
import com.epam.springadvanced.service.exception.UserNotRegisteredException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class, SecurityConfiguration.class})
public class BookingServiceTests
{
    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @DirtiesContext
    @Test
    public void testBookingServiceBookTicker()
            throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException
    {
        User user = userService.getById(1);
        LocalDateTime date = LocalDateTime.parse("2007-12-03T10:15:30");
        String name = "name";
        float price = 2.5f;
        Rating rating = Rating.HIGH;
        Event event = new Event(name, date, price, rating);
        Auditorium auditorium = auditoriumRepository.getById(1);
        event.setAuditorium(auditorium);
        eventRepository.save(event);
        Seat seat = new Seat(5);
        Ticket ticket = new Ticket(event, seat);
        bookingService.bookTicket(user, ticket);
        Collection<Ticket> tickets = bookingService.getTicketsForEvent(event, date);
        Assert.assertEquals(1, tickets.size());
    }
}
