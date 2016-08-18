package epam.spring.university.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

import epam.spring.university.domain.Auditorium;
import epam.spring.university.domain.Event;
import epam.spring.university.domain.EventRating;
import epam.spring.university.domain.Reservation;
import epam.spring.university.domain.Ticket;
import epam.spring.university.domain.User;
import epam.spring.university.service.BookingService;
import epam.spring.university.service.DiscountService;

public class BookingServiceImpl implements BookingService
{
    private Set<Ticket> tickets = new HashSet<>();
    private Set<Ticket> failedTickets = new HashSet<>();
    private List<Reservation> reservations = new ArrayList<>();

    private DiscountService discountService;

    private double vipSeatMulitplier;

    private int numberOfSeats;

    private BookingServiceImpl()
    {
    }

    public BookingServiceImpl(DiscountService discountService,
            @Value("#{auditoriumService.getByName('MoscowVision').getNumberOfSeats()}") int numberOfSeats)
    {
        this.discountService = discountService;
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public double getTicketsPrice(Event event, Date dateTime, User user, Set<Integer> seats)
    {
        double basePrice = event.getBasePrice();
        double ratingMultiplier = getRatingMultiplier(event.getRating());

        double discountPerTicket = discountService.getDiscount(user, event, dateTime, seats.size());
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        int vipSeats = 0;
        if (auditorium != null)
        {
            vipSeats = auditorium.countVipSeats(seats);
        }
        double totalMultiplier = ratingMultiplier * ((100 - discountPerTicket) / 100);
        return totalMultiplier * basePrice * (vipSeats * vipSeatMulitplier + (seats.size() - vipSeats));
    }

    private double getRatingMultiplier(EventRating rating)
    {
        switch (rating)
        {
            case LOW:
                return 0.8;
            case MID:
                return 1;
            case HIGH:
                return 1.2;
            default:
                break;
        }
        return 1;
    }

    @Override
    public void bookTickets(Set<Ticket> purchasedTickets)
    {
        for (Ticket ticket : purchasedTickets)
        {
            Event event = ticket.getEvent();
            Date eventDate = ticket.getDateTime();
            Reservation reservation = getReservationForEvent(event, eventDate);
            int seat = ticket.getSeat();
            if (reservation.getVacantSeats().contains(seat))
            {
                reserveSeat(reservation, seat);
                tickets.add(ticket);
            }
            else
            {
                recordFailedReservation(ticket);
            }
        }
    }

    private void recordFailedReservation(Ticket ticket)
    {
        failedTickets.add(ticket);
    }

    private void reserveSeat(Reservation reservation, int seat)
    {
        reservation.getVacantSeats().remove(seat);
    }

    private Reservation getReservationForEvent(Event event, Date eventDate)
    {
        List<Reservation> result = getReservationsForEvent(event, eventDate);
        if (result.size() == 1)
        {
            return result.get(0);
        }
        Reservation reservation = new Reservation(event, eventDate, numberOfSeats);
        reservations.add(reservation);
        return reservation;
    }

    private List<Reservation> getReservationsForEvent(Event event, Date eventDate)
    {
        return reservations.stream().filter(r -> r.getEvent().equals(event) && r.getEventDateTime().equals(eventDate))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(Event event, Date dateTime)
    {
        return tickets.stream().filter(t -> t.getEvent().equals(event) && t.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }

    @Override
    public void removeAllTickets()
    {
        tickets.clear();
        failedTickets.clear();
        reservations.clear();
    }

    @Override
    public Set<Ticket> getAll()
    {
        return tickets;
    }

    @Override
    public double getVipSeatMulitplier()
    {
        return vipSeatMulitplier;
    }

    public void setVipSeatMulitplier(double vipSeatMulitplier)
    {
        this.vipSeatMulitplier = vipSeatMulitplier;
    }
}
