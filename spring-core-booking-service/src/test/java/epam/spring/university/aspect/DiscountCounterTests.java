package epam.spring.university.aspect;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.User;
import epam.spring.university.service.BookingService;
import epam.spring.university.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = Config.class)
@ContextConfiguration("classpath:spring-core.xml")
public class DiscountCounterTests
{

    @Autowired
    private DiscountCounterAspect discountCounter;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    private static String BIRTHDAY_DISCOUNT = "epam.spring.university.service.discount.BirthdayDiscountStrategy";
    private static String DATETIME_DISCOUNT = "epam.spring.university.service.discount.DatetimeDiscountStrategy";
    private static String VOLUME_DISCOUNT = "epam.spring.university.service.discount.VolumeDiscountStrategy";

    private User user;
    private Map<User, Map<String, Integer>> counterMap;
    private Map<String, Integer> userData;

    @Before
    public void before()
    {
        counterMap = discountCounter.getDiscountCounter();
        user = userService.getById(1);
        userData = counterMap.get(user);
    }

    @Test
    @DirtiesContext
    public void testVolumeDiscountCounter()
    {
        Assert.assertNull(userData);
        Set<Integer> seats = IntStream.range(0, 10).boxed().collect(Collectors.toSet());
        bookingService.getTicketsPrice(new Event(), new DateTime().toDate(), user, seats);
        userData = counterMap.get(user);
        Assert.assertEquals(userData.get(VOLUME_DISCOUNT).intValue(), 1);
        Assert.assertNull(userData.get(BIRTHDAY_DISCOUNT));
    }

    @Test
    @DirtiesContext
    public void testDatetimeDiscountCounter()
    {
        Assert.assertNull(userData);
        Set<Integer> seats = Collections.singleton(1);
        Date date = new DateTime("2016-10-10T14:00:00").toDate();
        bookingService.getTicketsPrice(new Event(), date, user, seats);
        userData = counterMap.get(user);
        Assert.assertEquals(userData.get(DATETIME_DISCOUNT).intValue(), 1);
        Assert.assertNull(userData.get(BIRTHDAY_DISCOUNT));
        Assert.assertNull(userData.get(VOLUME_DISCOUNT));
    }

    @Test
    @DirtiesContext
    public void testBirthdayDiscountCounter()
    {
        user.setBirthDate(new DateTime().toDate());
        Assert.assertNull(userData);
        Set<Integer> seats = Collections.singleton(1);
        Event event = new Event();
        event.setAirDates(new TreeSet<Date>(Arrays.asList(user.getBirthDate())));
        bookingService.getTicketsPrice(event, user.getBirthDate(), user, seats);
        userData = counterMap.get(user);
        Assert.assertEquals(userData.get(BIRTHDAY_DISCOUNT).intValue(), 1);
        Assert.assertNull(userData.get(VOLUME_DISCOUNT));
    }
}
