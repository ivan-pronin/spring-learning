package epam.spring.university.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.Ticket;
import epam.spring.university.domain.User;
import epam.spring.university.service.BookingService;
import epam.spring.university.service.TicketService;

public class TicketServiceImpl implements TicketService
{
    private BookingService bookingService;

    private Set<Ticket> tickets;

    public TicketServiceImpl(BookingService bookingService)
    {
        this.bookingService = bookingService;
    }

    @Override
    public Ticket save(Ticket object)
    {
        if (tickets.add(object))
        {
            return object;
        }
        return null;
    }

    @Override
    public void remove(Ticket object)
    {
        tickets.remove(object);
    }

    @Override
    public Ticket getById(int id)
    {
        Set<Ticket> results = tickets.stream().filter(t -> t.getId() == id).collect(Collectors.toSet());
        return results.isEmpty() ? null : results.iterator().next();
    }

    @Override
    public Set<Ticket> getAll()
    {
        return tickets;
    }

    @Override
    public Set<Ticket> getTicketsByEvent(Event event)
    {
        return tickets.stream().filter(t -> t.getEvent().equals(event)).collect(Collectors.toSet());
    }

    @Override
    public Set<Ticket> getTicketsByUser(User user)
    {
        return tickets.stream().filter(t -> t.getUser().equals(user)).collect(Collectors.toSet());
    }

    @SuppressWarnings("unused")
    private void init()
    {
        tickets = bookingService.getAll();
    }

    @Override
    public void removeAllTickets()
    {
        tickets.clear();
    }
}
