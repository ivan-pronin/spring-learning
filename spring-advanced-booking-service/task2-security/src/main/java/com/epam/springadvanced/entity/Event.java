package com.epam.springadvanced.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Event
{
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private float ticketPrice;
    private Rating rating;
    private Auditorium auditorium;

    public Event()
    {
    }

    public Event(long id, String name, LocalDateTime dateTime, float ticketPrice, Rating rating)
    {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
    }

    public Event(String name, LocalDateTime dateTime, float ticketPrice, Rating rating)
    {
        this.name = name;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    public float getTicketPrice()
    {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice)
    {
        this.ticketPrice = ticketPrice;
    }

    public Rating getRating()
    {
        return rating;
    }

    public void setRating(Rating rating)
    {
        this.rating = rating;
    }

    public Auditorium getAuditorium()
    {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium)
    {
        this.auditorium = auditorium;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, dateTime);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (obj.getClass() != Event.class)
        {
            return false;
        }
        Event other = (Event) obj;
        return Objects.equals(name, other.name) && Objects.equals(dateTime, other.dateTime);
    }

    @Override
    public String toString()
    {
        return "Event [id=" + id + ", name=" + name + ", dateTime=" + dateTime + ", ticketPrice=" + ticketPrice + "]";
    }
}
