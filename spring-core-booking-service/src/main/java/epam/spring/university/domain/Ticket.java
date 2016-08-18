package epam.spring.university.domain;

import java.util.Date;
import java.util.Objects;

/**
 * @author Yuriy_Tkach
 */
public class Ticket extends DomainObject implements Comparable<Ticket>
{
    private User user;

    private Event event;

    private Date dateTime;

    private int seat;

    public Ticket(User user, Event event, Date dateTime, int seat)
    {
        this.user = user;
        this.event = event;
        this.dateTime = dateTime;
        this.seat = seat;
    }

    public User getUser()
    {
        return user;
    }

    public Event getEvent()
    {
        return event;
    }

    public Date getDateTime()
    {
        return dateTime;
    }

    public int getSeat()
    {
        return seat;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(dateTime, event, seat);
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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Ticket other = (Ticket) obj;
        return Objects.equals(user, other.getUser()) && Objects.equals(event, other.getEvent())
                && seat == other.getSeat();
    }

    @Override
    public int compareTo(Ticket other)
    {
        if (other == null)
        {
            return 1;
        }
        int result = dateTime.compareTo(other.getDateTime());

        if (result == 0)
        {
            result = event.getName().compareTo(other.getEvent().getName());
        }
        if (result == 0)
        {
            result = Long.compare(seat, other.getSeat());
        }
        return result;
    }

    @Override
    public String toString()
    {
        return "Ticket [user=" + user + ", event=" + event + ", dateTime=" + dateTime + ", seat=" + seat + "]";
    }

}
