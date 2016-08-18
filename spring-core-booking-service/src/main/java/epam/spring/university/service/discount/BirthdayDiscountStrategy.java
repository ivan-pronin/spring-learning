package epam.spring.university.service.discount;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.User;

@Component
public class BirthdayDiscountStrategy implements IDiscountStrategy
{

    private static final int DISCOUNT_DAYS_PERIOD = 5;

    @Override
    public double getDiscount(User user, Event event, Date dateTime, int numberOfTickets)
    {
        Date birthDate = user.getBirthDate();
        if (birthDate != null)
        {
            if (daysBetweenDates(dateTime, birthDate) <= DISCOUNT_DAYS_PERIOD)
            {
                return DiscountType.BIRTHDAY.getDiscount();
            }
        }
        return 0;
    }

    private int daysBetweenDates(Date dateTime, Date birthDate)
    {
        return Math.abs(new DateTime(birthDate).getDayOfYear() - new DateTime(dateTime).getDayOfYear());
    }
}
