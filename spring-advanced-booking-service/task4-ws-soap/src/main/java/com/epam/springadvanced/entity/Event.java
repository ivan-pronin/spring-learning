package com.epam.springadvanced.entity;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.epam.springadvanced.service.Rating;

// @XmlType(namespace = "http://www.example.org/event")
// @XmlRootElement(name = "event")
// @XmlType(name = "")
@XmlRootElement
public class Event
{

    private Long id;

    private String name;

    private java.time.LocalDateTime dateTime;

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

    @XmlAttribute
    public Long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @XmlElement
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @XmlElement
    public java.time.LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(java.time.LocalDateTime dateTime)

    {
        this.dateTime = dateTime;
    }

    @XmlElement
    public float getTicketPrice()
    {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice)
    {
        this.ticketPrice = ticketPrice;
    }

    @XmlElement
    public Rating getRating()
    {
        return rating;
    }

    public void setRating(Rating rating)
    {
        this.rating = rating;
    }

    @XmlElement
    public Auditorium getAuditorium()
    {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium)
    {
        this.auditorium = auditorium;
    }
}
