package epam.spring.university.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.joda.time.DateTime;

import epam.spring.university.domain.Auditorium;
import epam.spring.university.domain.Event;
import epam.spring.university.domain.EventRating;
import epam.spring.university.domain.User;

public class AbstractEventTests
{
    private static final String PAST_DATE = "2015-01-25T14:00:06";
    private static final String FUTURE_DATE = "2016-12-25T19:00:06";
    private static final String FUTURE_DATE_PLUS_ONE = "2016-12-26T19:00:06";
    private static final int BASE_PRICE = 20;
    private Event event;
    private Date eventDate;
    private User user;

    public void before()
    {
        Auditorium auditorium = new Auditorium("Cinema", 100, new HashSet<>(Arrays.asList(20, 21, 22)));
        NavigableSet<Date> dates = createDatesSet(FUTURE_DATE, PAST_DATE);
        NavigableMap<Date, Auditorium> auditoriums = createAuditoriumsMap(dates, auditorium);
        event = new Event("SuperFilm", dates, BASE_PRICE, EventRating.MID, auditoriums);
        eventDate = createDateFromString(FUTURE_DATE);
        user = new User("fName", "lName", "email");
    }

    protected NavigableSet<Date> createDatesSet(String... dates)
    {
        NavigableSet<Date> result = new TreeSet<Date>();
        for (String arg : dates)
        {
            Date date = createDateFromString(arg);
            result.add(date);
        }
        return result;
    }

    protected Date createDateFromString(String arg)
    {
        return new DateTime(arg).toDate();
    }

    protected NavigableMap<Date, Auditorium> createAuditoriumsMap(NavigableSet<Date> dates, Auditorium auditorium)
    {
        NavigableMap<Date, Auditorium> resultMap = new TreeMap<>();
        for (Date d : dates)
        {
            resultMap.put(d, auditorium);
        }
        return resultMap;
    }

    protected static String getFutureDate()
    {
        return FUTURE_DATE;
    }

    protected Event getEvent()
    {
        return event;
    }

    protected Date getEventDate()
    {
        return eventDate;
    }

    protected User getUser()
    {
        return user;
    }

    protected static int getBasePrice()
    {
        return BASE_PRICE;
    }

    protected static String getFutureDatePlusOne()
    {
        return FUTURE_DATE_PLUS_ONE;
    }

}
