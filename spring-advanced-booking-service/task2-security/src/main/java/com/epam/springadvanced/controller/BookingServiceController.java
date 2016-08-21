/*
 * =============================================================================
 *
 * Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * =============================================================================
 */
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
    private static final String EVENT_1_DATE = "2016-11-03T21:00:00";

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    public BookingServiceController()
    {
        super();
    }
    
    @RequestMapping(value = "/booking-service")
    public String openMainModelPage(final Booking booking, final BindingResult bindingResult, final ModelMap model)
    {
        return "/booking-service";
    }
   

    @RequestMapping(value = "/booking-service", params = {"getTicketPrice"})
    public String getTicketPrice(final Booking booking, final BindingResult bindingResult, final ModelMap model)
            throws UserNotRegisteredException, EventNotAssignedException
    {
        Set<Integer> seats = Arrays.asList(booking.getSeats().split(",")).stream().map(s -> Integer.parseInt(s))
                .collect(Collectors.toSet());
        Event event = eventService.getById(booking.getEventId());
        User user = userService.getById(booking.getUserId());
        float price = bookingService.getTicketPrice(event, LocalDateTime.parse(EVENT_1_DATE), seats, user);
        model.addAttribute("ticketPrice", price);
        return "booking-service";
    }

    @RequestMapping(value = "/booking-service", params = {"bookTicket"})
    public String bookTicket(final Booking booking, final BindingResult bindingResult, final ModelMap model)
            throws UserNotRegisteredException, TicketAlreadyBookedException, TicketWithoutEventException
    {
        User user = userService.getById(booking.getUserId());
        Ticket ticket = new Ticket(eventService.getById(booking.getEventId()),
                new Seat(Integer.parseInt(booking.getSeats()), booking.isVipSeat()));
        try
        {
            bookingService.bookTicket(user, ticket);
            model.addAttribute("bookResult", "Success!");
        }
        catch (UserNotRegisteredException | TicketAlreadyBookedException | TicketWithoutEventException e)
        {
            model.addAttribute("bookResult", "Failure =( ");
        }
        return "booking-service";
    }

    @RequestMapping(value = "/booking-service", params = {"getTickets"})
    public String getTickets(final Booking booking, final BindingResult bindingResult, final ModelMap model)
    {
        Event event = eventService.getById(booking.getEventId());
        List<Ticket> tickets = (List<Ticket>) bookingService.getTicketsForEvent(event,
                LocalDateTime.parse(EVENT_1_DATE));
        model.addAttribute("tickets", tickets);
        System.out.println(tickets);
        return "booking-service";
    }
}
