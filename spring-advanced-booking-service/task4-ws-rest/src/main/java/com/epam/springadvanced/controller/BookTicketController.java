package com.epam.springadvanced.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Seat;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.BookingService;
import com.epam.springadvanced.service.EventService;
import com.epam.springadvanced.service.UserService;
import com.epam.springadvanced.service.exception.EventNotAssignedException;
import com.epam.springadvanced.service.exception.TicketAlreadyBookedException;
import com.epam.springadvanced.service.exception.TicketWithoutEventException;
import com.epam.springadvanced.service.exception.UserNotRegisteredException;

@RestController
public class BookTicketController
{
    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/booking/getTicketPrice")
    public float getTicketPrice(@RequestParam(value = "eventId", required = true) long eventId,
            @RequestParam(value = "dateTime", required = true) String dateTime,
            @RequestParam(value = "seat", required = true) int seat,
            @RequestParam(value = "userId", required = true) long userId)
            throws UserNotRegisteredException, EventNotAssignedException
    {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);
        LocalDateTime date = LocalDateTime.parse(dateTime);
        return bookingService.getTicketPrice(event, date, Arrays.asList(seat), user);
    }

    @ResponseBody
    @RequestMapping("/booking/bookTicket")
    public String bookTicket(@RequestParam(value = "eventId", required = true) long eventId,
            @RequestParam(value = "seat", required = true) int seat,
            @RequestParam(value = "userId", required = true) long userId) throws UserNotRegisteredException,
            EventNotAssignedException, TicketAlreadyBookedException, TicketWithoutEventException
    {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);
        Ticket ticket = new Ticket(new Seat(seat), event);
        bookingService.bookTicket(user, ticket);
        return "Ticket was successfully booked!";
    }

    @ResponseBody
    @RequestMapping("/booking/getTicketsForEvent")
    public Collection<Ticket> getTicketsForEvent(@RequestParam(value = "eventId", required = true) long eventId,
            @RequestParam(value = "dateTime", required = true) String dateTime)
    {
        return bookingService.getTicketsForEvent(eventService.getById(eventId), LocalDateTime.parse(dateTime));
    }
}
