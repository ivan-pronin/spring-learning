package epam.spring.university.demo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.domain.Auditorium;
import epam.spring.university.domain.Event;
import epam.spring.university.domain.EventRating;
import epam.spring.university.domain.Ticket;
import epam.spring.university.domain.User;
import epam.spring.university.service.AuditoriumService;
import epam.spring.university.service.BookingService;
import epam.spring.university.service.EventService;
import epam.spring.university.service.UserService;
import epam.spring.university.service.discount.DiscountType;
import epam.spring.university.service.impl.AbstractEventTests;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = Config.class)
 @ContextConfiguration("classpath:spring-db.xml")
/**
 * This is a demo of task 3: Simple DB Access
 * @author Ivan_Pronin
 */
public class DbAccessDemoTests extends AbstractEventTests
{
    private static final int VIP_TICKETS_TO_BUY = 2;
    private static final String SOME_PAST_DATE = "1980-10-20";
    private static final String PAST_DATE_PLUS_DAY = "2016-10-21";
    private static final int BASE_TICKET_PRICE = 100;
    private static final Logger LOGGER = LoggerFactory.getLogger(DbAccessDemoTests.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditoriumService auditoriumService;

    @AfterClass
    public static void after()
    {
        removeDbFolder();
    }

    @BeforeClass
    public static void beforeTest()
    {
        removeDbFolder();
    }

    @Test
    public void testBuyTicketForNewEventWithDiscount()
    {
        LOGGER.info("Starting demo scenario 3-1 (Simple DB Access): add new user, add new event, buy a ticket for "
                + "that event for that user with birthday discount");
        User user = new User("James", "Milner", "jmilner@mail.com");
        user.setBirthDate(createDateFromString(SOME_PAST_DATE));
        int totalUsers = getTotalUsersCount();
        userService.save(user);
        Assert.assertEquals("New user was saved successfully", totalUsers + 1, getTotalUsersCount());

        Event event = createEvent(PAST_DATE_PLUS_DAY);
        int totalEvents = getTotalEventsCount();
        eventService.save(event);
        Assert.assertEquals("New event was saved successfully", totalEvents + 1, getTotalEventsCount());

        Date eventDateTime = createDateFromString(PAST_DATE_PLUS_DAY);
        double ticketPrice = bookingService.getTicketsPrice(event, eventDateTime, user,
                new HashSet<>(Arrays.asList(1)));
        Assert.assertEquals("Ticket price calculated correctly",
                BASE_TICKET_PRICE * (100 - DiscountType.BIRTHDAY.getDiscount()) / 100, ticketPrice, 0.1);

        Ticket ticket = new Ticket(user, event, eventDateTime, 1);
        int purchasedTicketsCount = getPurchasedTicketsCount(event, eventDateTime);
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        Assert.assertEquals("New ticket was purchased successfully", purchasedTicketsCount + 1,
                getPurchasedTicketsCount(event, eventDateTime));
    }

    @Test
    public void testBuyVipTicketsForNewHighRatedEvent()
    {
        LOGGER.info("Starting demo scenario 3-2(Simple DB Access): buy two vip tickets for registered user for existing"
                + " HIGH-rated event");
        User user = userService.getById(1);

        Event event = eventService.getById(1);
        EventRating highRating = EventRating.HIGH;
        event.setRating(highRating);
        double baseTicketPrice = event.getBasePrice();

         Set<Integer> vipSeats = auditoriumService.getByName("GrandCinema").getVipSeats();
//        Set<Integer> vipSeats = auditoriumService.getAll().iterator().next().getVipSeats();
        Assert.assertThat("There are at least two vip seats available", vipSeats.size(), Matchers.greaterThan(1));
        vipSeats = vipSeats.stream().limit(VIP_TICKETS_TO_BUY).collect(Collectors.toSet());

        Date eventDate = event.getAirDates().last();
        double ticketPrice = bookingService.getTicketsPrice(event, eventDate, user, vipSeats);
        double vipSeatMulitplier = bookingService.getVipSeatMulitplier();
        double ratingMultiplier = highRating.getMultiplier();
        Assert.assertEquals("Vip tickets price calculated correctly",
                baseTicketPrice * VIP_TICKETS_TO_BUY * vipSeatMulitplier * ratingMultiplier, ticketPrice, 0.1);

        int purchasedTickets = getPurchasedTicketsCount(event, eventDate);
        Set<Ticket> orderedTickets = createTickets(user, event, eventDate, vipSeats, VIP_TICKETS_TO_BUY);
        bookingService.bookTickets(orderedTickets);
        Assert.assertEquals("New tickets were purchased successfully", purchasedTickets + VIP_TICKETS_TO_BUY,
                getPurchasedTicketsCount(event, eventDate));
    }

    private Event createEvent(String eventDateTimeString)
    {
        Auditorium auditorium = new Auditorium("Cinema", 100, new HashSet<>(Arrays.asList(20, 21, 22)));
        NavigableSet<Date> dates = createDatesSet(eventDateTimeString);
        NavigableMap<Date, Auditorium> auditoriums = createAuditoriumsMap(dates, auditorium);
        return new Event("SuperFilm", dates, BASE_TICKET_PRICE, EventRating.MID, auditoriums);
    }

    private Set<Ticket> createTickets(User user, Event event, Date eventDate, Set<Integer> seatsPool, int seatsCount)
    {
        Set<Ticket> tickets = new HashSet<>();
        Iterator<Integer> seats = seatsPool.iterator();
        for (int i = 0; i < seatsCount; i++)
        {
            Ticket ticket = new Ticket(user, event, eventDate, seats.next());
            tickets.add(ticket);
        }
        return tickets;
    }

    private static void removeDbFolder()
    {
        try
        {
            File directory = new File("spring_db");
            if (directory.exists())
            {
                FileUtils.deleteDirectory(directory);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private int getPurchasedTicketsCount(Event event, Date eventDateTime)
    {
        return bookingService.getPurchasedTicketsForEvent(event, eventDateTime).size();
    }

    private int getTotalEventsCount()
    {
        return eventService.getAll().size();
    }

    private int getTotalUsersCount()
    {
        return userService.getAll().size();
    }
}
