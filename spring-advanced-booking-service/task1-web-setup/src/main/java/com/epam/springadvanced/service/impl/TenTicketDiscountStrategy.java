package com.epam.springadvanced.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.TicketRepository;
import com.epam.springadvanced.service.DiscountStrategy;

@Component
public class TenTicketDiscountStrategy implements DiscountStrategy {

    private static final Logger log = LoggerFactory.getLogger(TenTicketDiscountStrategy.class);

    private static final int EACH_N_TICKET = 10;
    private static final float DISCOUNT = 0.5F;
    private static final float DEFAULT_DISCOUNT = 0;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public float calculate(User user) {
        final float[] discount = {DEFAULT_DISCOUNT};
        Optional.ofNullable(user)
                .ifPresent(u -> {
                    int count = ticketRepository.getBookedTicketsByUserId(u.getId()).size();
                    if (count > 0) {
                        discount[0] = (count % EACH_N_TICKET) == 0 ? DISCOUNT : DEFAULT_DISCOUNT;
                    }
                });

        if (discount[0] == DISCOUNT) {
            log.info("User " + user.getName() + " purchased tenth ticket!");
        }

        return discount[0];
    }
}
