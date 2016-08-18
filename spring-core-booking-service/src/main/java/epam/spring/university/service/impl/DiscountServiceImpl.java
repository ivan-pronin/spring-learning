package epam.spring.university.service.impl;

import java.util.Date;
import java.util.Set;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.User;
import epam.spring.university.service.DiscountService;
import epam.spring.university.service.discount.IDiscountStrategy;

public class DiscountServiceImpl implements DiscountService
{
    private Set<IDiscountStrategy> discountStrategies;

    public DiscountServiceImpl()
    {
    }

    public DiscountServiceImpl(Set<IDiscountStrategy> discountStrategies)
    {
        this.discountStrategies = discountStrategies;

    }

    @Override
    public double getDiscount(User user, Event event, Date dateTime, int numberOfTickets)
    {
        double discount = 0;
        for (IDiscountStrategy strategy : discountStrategies)
        {
            double maxDicount = strategy.getDiscount(user, event, dateTime, numberOfTickets);
            if (maxDicount > discount)
            {
                discount = maxDicount;
            }
        }
        return discount;
    }

    public Set<IDiscountStrategy> getDiscountStrategies()
    {
        return discountStrategies;
    }
}
