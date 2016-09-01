package com.epam.springadvanced.ws.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.epam.springadvanced.entity.Event;

@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"event"})
@XmlRootElement(name = "getEventByIdResponse")
public class GetEventByIdResponse
{
    @XmlElement(required = true)
    private Event event;

    public GetEventByIdResponse()
    {
    }

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }
}
