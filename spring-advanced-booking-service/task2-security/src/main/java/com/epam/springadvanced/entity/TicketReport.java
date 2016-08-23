package com.epam.springadvanced.entity;

public class TicketReport
{
    private long id;
    private float price;
    private int seat;
    private long eventId;
    private String eventName;

    public TicketReport(long id, float price, int seat, long eventId, String eventName)
    {
        this.id = id;
        this.price = price;
        this.seat = seat;
        this.eventId = eventId;
        this.eventName = eventName;
    }

    public Long getId()
    {
        return id;
    }

    public float getPrice()
    {
        return price;
    }

    public int getSeat()
    {
        return seat;
    }

    public Long getEventId()
    {
        return eventId;
    }

    public String getEventName()
    {
        return eventName;
    }
}