package com.epam.springadvanced.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epam.springadvanced.entity.Booking;
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

@Controller
public class BookingServiceController
{
    private static final String BOOK_RESULT = "bookResult";
    private static final String BOOKING_SERVICE = "booking-service";
    private static final String BOOKING_SERVICE_PATH = "/booking-service";
    private static final String EVENT_1_DATE = "2016-11-03T21:00:00";

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = BOOKING_SERVICE_PATH)
    public String openMainModelPage(final Booking booking, final BindingResult bindingResult, final ModelMap model)
    {
        return BOOKING_SERVICE_PATH;
    }

    @RequestMapping(value = BOOKING_SERVICE_PATH, params = {"getTicketPrice"})
    public String getTicketPrice(final Booking booking, final BindingResult bindingResult, final ModelMap model)
            throws UserNotRegisteredException, EventNotAssignedException
    {
        Set<Integer> seats = Arrays.asList(booking.getSeats().split(",")).stream().map(s -> Integer.parseInt(s))
                .collect(Collectors.toSet());
        Event event = eventService.getById(booking.getEventId());
        User user = userService.getById(booking.getUserId());
        float price = bookingService.getTicketPrice(event, LocalDateTime.parse(EVENT_1_DATE), seats, user);
        model.addAttribute("ticketPrice", price);
        return BOOKING_SERVICE;
    }

    @RequestMapping(value = BOOKING_SERVICE_PATH, params = {"bookTicket"})
    public String bookTicket(final Booking booking, final BindingResult bindingResult, final ModelMap model)
            throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException
    {
        User user = userService.getById(booking.getUserId());
        Ticket ticket = new Ticket(eventService.getById(booking.getEventId()),
                new Seat(Integer.parseInt(booking.getSeats()), booking.isVipSeat()));
        try
        {
            bookingService.bookTicket(user, ticket);
            model.addAttribute(BOOK_RESULT, "Success!");
        }
        catch (UserNotRegisteredException | TicketAlreadyBookedException | TicketWithoutEventException e)
        {
            model.addAttribute(BOOK_RESULT, "Failure =( ");
        }
        return BOOKING_SERVICE;
    }

    @RequestMapping(value = BOOKING_SERVICE_PATH, params = {"getTickets"})
    public String getTickets(final Booking booking, final BindingResult bindingResult, final ModelMap model)
    {
        Event event = eventService.getById(booking.getEventId());
        List<Ticket> tickets = (List<Ticket>) bookingService.getTicketsForEvent(event,
                LocalDateTime.parse(EVENT_1_DATE));
        model.addAttribute("tickets", tickets);
        return BOOKING_SERVICE;
    }
}
