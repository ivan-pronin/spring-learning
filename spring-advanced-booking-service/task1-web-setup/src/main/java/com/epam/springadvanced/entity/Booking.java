package com.epam.springadvanced.entity;

public class Booking
{
    private Long userId;
    private Long eventId;
    private String seats;
    private boolean vipSeat;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getEventId()
    {
        return eventId;
    }

    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    public String getSeats()
    {
        return seats;
    }

    public void setSeats(String seats)
    {
        this.seats = seats;
    }

    @Override
    public String toString()
    {
        return "Booking [userId=" + userId + ", eventId=" + eventId + ", seats=" + seats + "]";
    }

    public boolean isVipSeat()
    {
        return vipSeat;
    }

    public void setVipSeat(boolean vipSeat)
    {
        this.vipSeat = vipSeat;
    }

}
