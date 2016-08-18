package epam.spring.university.aspect;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.Ticket;

@Aspect
@Component
public class EventCounterAspect
{
    private Map<Event, Integer> bookingCounter = new HashMap<>();
    private Map<Event, Integer> nameCounter = new HashMap<>();
    private Map<Event, Integer> priceCounter = new HashMap<>();

    @AfterReturning(pointcut = "onEventServiceGetByName()", returning = "event")
    public void countGetName(Event event)
    {
        updateMap(nameCounter, event);
    }

    @Before("onBookingServiceGetTicketsPrice(epam.spring.university.domain.Event) && args(event, ..)")
    public void countGetTicketsPrice(Event event)
    {
        updateMap(priceCounter, event);
    }

    @Before("onBookingServiceBookTickets(*) && args(tickets)")
    public void countGetTicketsPrice(Set<Ticket> tickets)
    {
        for (Ticket ticket : tickets)
        {
            Event event = ticket.getEvent();
            updateMap(bookingCounter, event);
        }
    }

    @Pointcut("execution(* epam.spring.university.service.BookingService.bookTickets(..)) && args(tickets)")
    public void onBookingServiceBookTickets(Set<Ticket> tickets)
    {
    }

    @Pointcut("execution(* epam.spring.university.service.BookingService.getTicketsPrice(..)) && args(event, ..)")
    public void onBookingServiceGetTicketsPrice(Event event)
    {
    }

    @Pointcut("execution(* epam.spring.university.service.EventService.getByName(..))")
    public void onEventServiceGetByName()
    {
    }

    public Map<Event, Integer> getBookingCounter()
    {
        return bookingCounter;
    }

    public Map<Event, Integer> getNameCounter()
    {
        return nameCounter;
    }

    public Map<Event, Integer> getPriceCounter()
    {
        return priceCounter;
    }

    private void updateMap(Map<Event, Integer> map, Event event)
    {
        Integer count = map.get(event);
        if (count == null)
        {
            map.put(event, 1);
            return;
        }
        map.put(event, ++count);
    }
}
