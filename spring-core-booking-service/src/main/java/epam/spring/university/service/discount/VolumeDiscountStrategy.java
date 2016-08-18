package epam.spring.university.service.discount;

import java.util.Date;

import org.springframework.stereotype.Component;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.User;

@Component
public class VolumeDiscountStrategy implements IDiscountStrategy
{

    private static final int DISCOUT_VOLUME = 10;

    @Override
    public double getDiscount(User user, Event event, Date dateTime, int numberOfTickets)
    {
        int discountCount = calculateTicketsNumberWithDiscount(numberOfTickets);
        if (discountCount > 0)
        {
            return discountCount * DiscountType.VOLUME.getDiscount() / numberOfTickets;
        }
        return 0;
    }

    private int calculateTicketsNumberWithDiscount(int numberOfTickets)
    {
        return numberOfTickets / DISCOUT_VOLUME;
    }

}
