package epam.spring.university.aspect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.Ticket;
import epam.spring.university.domain.User;
import epam.spring.university.service.BookingService;
import epam.spring.university.service.EventService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = Config.class)
@ContextConfiguration("classpath:spring-core.xml")
public class EventCounterAspectTests
{

    @Autowired
    private EventCounterAspect eventCounterAspect;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Test
    public void testEventNameCounter()
    {
        Map<Event, Integer> counter = eventCounterAspect.getNameCounter();
        int initialCount = counter.size();
        eventService.getByName("someName");
        assertMapSize(counter, ++initialCount);
    }

    @Test
    public void testGetPriceCounter()
    {
        Map<Event, Integer> counter = eventCounterAspect.getPriceCounter();
        int initialCount = counter.size();
        bookingService.getTicketsPrice(new Event(), new DateTime().toDate(), new User(),
                new HashSet<>(Arrays.asList(1)));
        assertMapSize(counter, ++initialCount);
    }

    @Test
    public void testBookTicketsCounter()
    {
        Map<Event, Integer> counter = eventCounterAspect.getBookingCounter();
        int initialCount = counter.size();
        Ticket ticket = new Ticket(new User(), new Event(), new DateTime().toDate(), 1);
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        assertMapSize(counter, ++initialCount);
    }

    private void assertMapSize(Map<Event, Integer> nameCounter, int expectedSize)
    {
        Assert.assertEquals(nameCounter.size(), expectedSize);
    }
}
