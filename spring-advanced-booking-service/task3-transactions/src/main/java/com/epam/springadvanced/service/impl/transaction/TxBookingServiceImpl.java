package com.epam.springadvanced.service.impl.transaction;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.service.BookingService;
import com.epam.springadvanced.service.exception.EventNotAssignedException;
import com.epam.springadvanced.service.exception.NotEnoughBalanceException;
import com.epam.springadvanced.service.exception.TicketAlreadyBookedException;
import com.epam.springadvanced.service.exception.TicketWithoutEventException;
import com.epam.springadvanced.service.exception.UserNotRegisteredException;

@Service
@Qualifier("txBookingServiceImpl")
public class TxBookingServiceImpl implements BookingService
{
    @Autowired
    @Qualifier("bookingServiceImpl")
    private BookingService bookingService;

    @Autowired
    private PlatformTransactionManager txManager;

    @Override
    public float getTicketPrice(Event event, LocalDateTime dateTime, Collection<Integer> seatNumbers, User user)
            throws UserNotRegisteredException, EventNotAssignedException
    {
        return bookingService.getTicketPrice(event, dateTime, seatNumbers, user);
    }

    @Override
    public void bookTicket(User user, Ticket ticket) throws UserNotRegisteredException, TicketAlreadyBookedException,
            TicketWithoutEventException, EventNotAssignedException, NotEnoughBalanceException
    {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("bookTicketTransaction");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = txManager.getTransaction(def);
        try
        {
            bookingService.bookTicket(user, ticket);
        }
        catch (NotEnoughBalanceException ex)
        {
            txManager.rollback(status);
            throw ex;
        }
        txManager.commit(status);
    }

    @Override
    public Collection<Ticket> getTicketsForEvent(Event event, LocalDateTime dateTime)
    {
        return bookingService.getTicketsForEvent(event, dateTime);
    }

}
