package com.epam.springadvanced.entity;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.epam.springadvanced.DateTimeAdapter;
import com.epam.springadvanced.service.Rating;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name="event")
public class Event
{
    @XmlAttribute
    private Long id;
    @XmlElement
    private String name;
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private java.time.LocalDateTime dateTime;
    @XmlElement
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
    
    public java.time.LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(java.time.LocalDateTime dateTime)

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
}
