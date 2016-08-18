package epam.spring.university.service.impl;

import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.domain.Ticket;
import epam.spring.university.domain.User;
import epam.spring.university.service.BookingService;
import epam.spring.university.service.discount.DiscountType;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes=Config.class)
@ContextConfiguration("classpath:spring-core.xml")
public class BookingServiceImplTests extends AbstractEventTests
{
    private static final int TWO_TICKETS = 2;

    @Autowired
    private BookingService bookingService;

    @Override
    @Before
    public void before()
    {
        super.before();
        bookingService.removeAllTickets();
    }

    @Test
    public void testBookTickets()
    {
        Assert.assertThat(bookingService.getPurchasedTicketsForEvent(getEvent(), getEventDate()).size(), equalTo(0));
        Ticket ticket = new Ticket(new User(), getEvent(), getEventDate(), 1);
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        Assert.assertThat(bookingService.getPurchasedTicketsForEvent(getEvent(), getEventDate()).size(), equalTo(1));
    }

    @Test
    public void testGetPurchasedTicketsForEvent()
    {
        Date date = createDateFromString(getFutureDate());
        Ticket ticket = new Ticket(getUser(), getEvent(), date, 1);
        bookingService.bookTickets(new HashSet<>(Arrays.asList(ticket)));
        Assert.assertEquals(ticket, bookingService.getPurchasedTicketsForEvent(getEvent(), date).iterator().next());
    }

    @Test
    public void testGetTicketsPriceNoDiscount()
    {
        Assert.assertEquals(getBasePrice() * TWO_TICKETS, bookingService.getTicketsPrice(getEvent(), getEventDate(),
                getUser(), new HashSet<>(Arrays.asList(1, 2))), 0.1);
    }

    @Test
    public void testGetTicketsPriceBirthdayDiscount()
    {
        User bornUser = new User();
        bornUser.setBirthDate(createDateFromString(getFutureDatePlusOne()));
        Assert.assertEquals(getBasePrice() * (100 - DiscountType.BIRTHDAY.getDiscount()) / 100,
                bookingService.getTicketsPrice(getEvent(), getEventDate(), bornUser, new HashSet<>(Arrays.asList(1))),
                0.1);
    }

    @Test
    public void testGetTicketsPriceVolumeDiscount()
    {
        Assert.assertEquals(getBasePrice() * 9 + getBasePrice() * (100 - DiscountType.VOLUME.getDiscount()) / 100,
                bookingService.getTicketsPrice(getEvent(), getEventDate(), getUser(),
                        IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toSet())),
                0.1);
    }

}
