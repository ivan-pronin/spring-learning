package com.epam.springadvanced.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epam.springadvanced.entity.Auditorium;
import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Rating;
import com.epam.springadvanced.entity.Seat;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.AuditoriumRepository;
import com.epam.springadvanced.repository.TicketRepository;
import com.epam.springadvanced.service.BookingService;
import com.epam.springadvanced.service.DiscountService;
import com.epam.springadvanced.service.UserAccountService;
import com.epam.springadvanced.service.UserService;
import com.epam.springadvanced.service.exception.EventNotAssignedException;
import com.epam.springadvanced.service.exception.NotEnoughBalanceException;
import com.epam.springadvanced.service.exception.TicketAlreadyBookedException;
import com.epam.springadvanced.service.exception.TicketWithoutEventException;
import com.epam.springadvanced.service.exception.UserNotRegisteredException;

@Service
@Qualifier("bookingServiceImpl")
public class BookingServiceImpl implements BookingService
{
    private static final int PERCENT_100 = 100;
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
    private static final int VIP_PRICE_COEF = 2;
    private static final float HIGH_EVENT_PRICE_COEF = 1.2f;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private AuditoriumRepository auditoriumRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public float getTicketPrice(Event event, LocalDateTime dateTime, Collection<Integer> seatNumbers, User user)
            throws UserNotRegisteredException, EventNotAssignedException
    {
        float price = 0;
        Optional.ofNullable(user).flatMap(u -> Optional.ofNullable(userService.getUserByName(u.getName())))
                .orElseThrow(UserNotRegisteredException::new);

        Optional.ofNullable(event).flatMap(e -> Optional.ofNullable(e.getAuditorium()))
                .orElseThrow(EventNotAssignedException::new);

        Auditorium auditorium = event.getAuditorium();
        Collection<Seat> auditoriumSeats = auditorium.getSeats();
        for (int number : seatNumbers)
        {
            Seat seat = auditoriumRepository.getSeatByAuditoriumIdAndNumber(auditorium.getId(), number);
            if (auditoriumSeats.contains(seat))
            {
                price = seat.isVip() ? event.getTicketPrice() * VIP_PRICE_COEF : event.getTicketPrice();
            }
            price = price * (event.getRating() == Rating.HIGH ? HIGH_EVENT_PRICE_COEF : 1);
            float discount = discountService.getDiscount(user, event, dateTime);
            price = price - (PERCENT_100 * discount);
        }

        return price;
    }

    @Override
    public void bookTicket(User user, Ticket ticket) throws UserNotRegisteredException, TicketAlreadyBookedException,
            TicketWithoutEventException, EventNotAssignedException, NotEnoughBalanceException
    {
        // if users is null or not registered then throw exception
        Optional.ofNullable(user).flatMap(u -> Optional.ofNullable(userService.getUserByName(u.getName())))
                .orElseThrow(UserNotRegisteredException::new);

        // if ticket is null or without event then throw exception
        Optional.ofNullable(ticket).flatMap(t -> Optional.ofNullable(t.getEvent()))
                .orElseThrow(TicketWithoutEventException::new);

        // if user has not enough balance then throw exception
        Event event = ticket.getEvent();
        float ticketPrice = getTicketPrice(event, event.getDateTime(), Arrays.asList(ticket.getSeat().getNumber()),
                user);
        double userBalance = userAccountService.getUserBalance(user);
        if (Double.compare(userBalance, ticketPrice) < 0)
        {
            throw new NotEnoughBalanceException();
        }

        // false if ticket already booked for specified event and seat
        boolean notBooked = ticketRepository.getBookedTickets().stream().noneMatch(
            t -> t.getEvent().equals(ticket.getEvent()) && t.getSeat().getNumber() == ticket.getSeat().getNumber());
        if (notBooked)
        {
            ticketRepository.saveBookedTicket(user, ticket);
            userAccountService.decreaseBalance(user, ticketPrice);
            LOGGER.info(String.format("User <%s> booked ticket with seat number %d for event <%s>", user.getName(),
                    ticket.getSeat().getNumber(), ticket.getEvent().getName()));
        }
        else
        {
            throw new TicketAlreadyBookedException(ticket.getSeat().getNumber());
        }
    }

    @Override
    public Collection<Ticket> getTicketsForEvent(Event event, LocalDateTime dateTime)
    {
        return ticketRepository.getByEventName(event.getName()).stream()
                .filter(t -> t.getEvent().getDateTime().isEqual(dateTime)).collect(Collectors.toList());
    }
}
