package com.epam.springadvanced.service.impl;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.DiscountStrategy;

@Component
public class BirthDayDiscountStrategy implements DiscountStrategy {

    private static final Logger log = LoggerFactory.getLogger(BirthDayDiscountStrategy.class);

    private static final float DISCOUNT = 0.05F;

    @Override
    public float calculate(User user) {
        float discount = 0;
        if (user != null && user.getBirthday() != null) {
            if (user.getBirthday().getDayOfMonth() == LocalDate.now().getDayOfMonth()
                    && user.getBirthday().getMonthValue() == LocalDate.now().getMonthValue()) {
                discount = DISCOUNT;
                log.info("Today is birthday of " + user.getName());
            }
        }
        return discount;
    }
}
