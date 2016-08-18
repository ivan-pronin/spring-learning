package epam.spring.university.service.impl;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.Ticket;
import epam.spring.university.domain.User;
import epam.spring.university.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes=Config.class)
@ContextConfiguration("classpath:spring-core.xml")
public class TicketServiceImplTests extends AbstractEventTests
{
    private static final int ID_23 = 23;

    @Autowired
    private TicketService ticketService;

    @Override
    @Before
    public void before()
    {
        super.before();
    }

    @After
    public void after()
    {
        ticketService.removeAllTickets();
    }

    @Test
    public void testGetAll()
    {
        Assert.assertThat(ticketService.getAll(), Matchers.empty());
    }

    @Test
    public void testGetById()
    {
        Ticket ticket = new Ticket(new User(), new Event(), createDateFromString(getFutureDate()), 1);
        ticket.setId(ID_23);
        ticketService.save(ticket);
        Assert.assertEquals(ticket, ticketService.getById(ID_23));
    }

    @Test
    public void testGetTicketsByEvent()
    {
        Event event = getEvent();
        Ticket ticket = new Ticket(new User(), event, createDateFromString(getFutureDate()), 1);
        ticketService.save(ticket);
        Assert.assertEquals(ticket, ticketService.getTicketsByEvent(event).iterator().next());
    }

    @Test
    public void testGetTicketsByUser()
    {
        User user = getUser();
        Ticket ticket = new Ticket(user, new Event(), createDateFromString(getFutureDate()), 1);
        ticketService.save(ticket);
        Assert.assertEquals(ticket, ticketService.getTicketsByUser(user).iterator().next());
    }

    @Test
    public void testRemove()
    {
        Ticket ticket = new Ticket(new User(), new Event(), createDateFromString(getFutureDate()), 1);
        ticketService.save(ticket);
        Assert.assertThat(ticketService.getAll().size(), Matchers.is(1));
        ticketService.remove(ticket);
        Assert.assertThat(ticketService.getAll(), Matchers.empty());
    }
}
