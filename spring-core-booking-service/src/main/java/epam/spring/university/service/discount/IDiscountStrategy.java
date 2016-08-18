package epam.spring.university.service.discount;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.User;

public interface IDiscountStrategy
{
    double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull Date dateTime, int numberOfTickets);
}
