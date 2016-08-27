package com.epam.springadvanced.service.transaction;

import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.springadvanced.config.SpringConfiguration;
import com.epam.springadvanced.entity.Auditorium;
import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Rating;
import com.epam.springadvanced.entity.Seat;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.AuditoriumRepository;
import com.epam.springadvanced.repository.EventRepository;
import com.epam.springadvanced.service.BookingService;
import com.epam.springadvanced.service.UserService;
import com.epam.springadvanced.service.exception.EventNotAssignedException;
import com.epam.springadvanced.service.exception.NotEnoughBalanceException;
import com.epam.springadvanced.service.exception.TicketAlreadyBookedException;
import com.epam.springadvanced.service.exception.TicketWithoutEventException;
import com.epam.springadvanced.service.exception.UserNotRegisteredException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TxBookingServiceImplTests
{
    @Autowired
    @Qualifier("txBookingServiceImpl")
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @DirtiesContext
    @Test
    public void testBookTicket()
    {
        User user = userService.getById(1);
        LocalDateTime date = LocalDateTime.parse("2012-10-27T10:15:30");
        String name = "eventName";
        Rating rating = Rating.HIGH;
        Event event = new Event(name, date, 3f, rating);
        Auditorium auditorium = auditoriumRepository.getById(1);
        event.setAuditorium(auditorium);
        eventRepository.save(event);
        Seat seat = new Seat(18);
        Ticket ticket = new Ticket(event, seat);
        try
        {
            bookingService.bookTicket(user, ticket);
        }
        catch (NotEnoughBalanceException | UserNotRegisteredException | TicketAlreadyBookedException
                | TicketWithoutEventException | EventNotAssignedException e)
        {
            e.printStackTrace();
        }
        Collection<Ticket> tickets = bookingService.getTicketsForEvent(event, date);
        Assert.assertEquals(1, tickets.size());
    }
}
