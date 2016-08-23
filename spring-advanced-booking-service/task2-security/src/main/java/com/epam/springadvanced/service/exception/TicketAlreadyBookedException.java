package com.epam.springadvanced.service.exception;

public class TicketAlreadyBookedException extends Exception
{
    private static final long serialVersionUID = 9135118286268804842L;
    private String message;

    public TicketAlreadyBookedException(int seatNumber)
    {
        message = String.format("Ticket for seat number %d  already booked!", seatNumber);
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
