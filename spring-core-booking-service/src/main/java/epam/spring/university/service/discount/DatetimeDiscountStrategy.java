package epam.spring.university.service.discount;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.User;

@Component
public class DatetimeDiscountStrategy implements IDiscountStrategy
{
    private static final int EARLY_HOUR_END = 16;
    private static final int EARLY_HOUR_START = 12;

    @Override
    public double getDiscount(User user, Event event, Date dateTime, int numberOfTickets)
    {
        DateTime time = new DateTime(dateTime);
        if (isEarlyTime(time.getHourOfDay()))
        {
            return DiscountType.DATETIME.getDiscount();
        }
        return 0;
    }

    private boolean isEarlyTime(int hourOfDay)
    {
        return hourOfDay >= EARLY_HOUR_START && hourOfDay <= EARLY_HOUR_END;
    }
}
