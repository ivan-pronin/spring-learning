package epam.spring.university.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

/**
 * @author Yuriy_Tkach
 */
@Component
public class Event extends DomainObject
{
    private NavigableSet<Date> airDates = new TreeSet<>();

    private NavigableMap<Date, Auditorium> auditoriums = new TreeMap<>();

    private double basePrice;

    private String name;

    private EventRating rating;

    public Event()
    {
    }

    public Event(String name, NavigableSet<Date> airDates, double basePrice, EventRating rating,
            NavigableMap<Date, Auditorium> auditoriums)
    {
        this.name = name;
        this.airDates = airDates;
        this.basePrice = basePrice;
        this.rating = rating;
        this.auditoriums = auditoriums;
    }

    /**
     * Add date and time of event air
     * @param dateTime
     * Date and time to add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    public boolean addAirDateTime(Date dateTime)
    {
        return airDates.add(dateTime);
    }

    /**
     * Adding date and time of event air and assigning auditorium to that
     * @param dateTime
     * Date and time to add
     * @param auditorium
     * Auditorium to add if success in date time add
     * @return <code>true</code> if successful, <code>false</code> if already
     * there
     */
    public boolean addAirDateTime(Date dateTime, Auditorium auditorium)
    {
        boolean result = airDates.add(dateTime);
        if (result)
        {
            auditoriums.put(dateTime, auditorium);
        }
        return result;
    }

    /**
     * Checks if event airs on particular date
     * @param date
     * Date to ckeck
     * @return <code>true</code> event airs on that date
     */
    public boolean airsOnDate(LocalDate date)
    {
        return airDates.stream().anyMatch(dt -> dt.equals(date));
    }

    /**
     * Checking if event airs on dates between <code>from</code> and
     * <code>to</code> inclusive
     * @param from
     * Start date to check
     * @param to
     * End date to check
     * @return <code>true</code> event airs on dates
     */
    public boolean airsOnDates(Date from, Date to)
    {
        return airDates.stream().anyMatch(dt -> dt.compareTo(from) >= 0 && dt.compareTo(to) <= 0);
    }

    /**
     * Checks if event airs on particular date and time
     * @param dateTime
     * Date and time to check
     * @return <code>true</code> event airs on that date and time
     */
    public boolean airsOnDateTime(LocalDateTime dateTime)
    {
        return airDates.stream().anyMatch(dt -> dt.equals(dateTime));
    }

    /**
     * Checks if event is aired on particular <code>dateTime</code> and assigns
     * auditorium to it.
     * @param dateTime
     * Date and time of aired event for which to assign
     * @param auditorium
     * Auditorium that should be assigned
     * @return <code>true</code> if successful, <code>false</code> if event is
     * not aired on that date
     */
    public boolean assignAuditorium(Date dateTime, Auditorium auditorium)
    {
        if (airDates.contains(dateTime))
        {
            auditoriums.put(dateTime, auditorium);
            return true;
        }
        else
        {
            return false;
        }
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
        Event other = (Event) obj;
        return Objects.equals(name, other.getName());
    }

    public NavigableSet<Date> getAirDates()
    {
        return airDates;
    }

    public NavigableMap<Date, Auditorium> getAuditoriums()
    {
        return auditoriums;
    }

    public double getBasePrice()
    {
        return basePrice;
    }

    public String getName()
    {
        return name;
    }

    public EventRating getRating()
    {
        return rating != null ? rating : EventRating.MID;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    /**
     * Removes the date and time of event air. If auditorium was assigned to
     * that date and time - the assignment is also removed
     * @param dateTime
     * Date and time to remove
     * @return <code>true</code> if successful, <code>false</code> if not there
     */
    public boolean removeAirDateTime(LocalDateTime dateTime)
    {
        boolean result = airDates.remove(dateTime);
        if (result)
        {
            auditoriums.remove(dateTime);
        }
        return result;
    }

    /**
     * Removes auditorium assignment from event
     * @param dateTime
     * Date and time to remove auditorium for
     * @return <code>true</code> if successful, <code>false</code> if not
     * removed
     */
    public boolean removeAuditoriumAssignment(LocalDateTime dateTime)
    {
        return auditoriums.remove(dateTime) != null;
    }

    public void setAirDates(NavigableSet<Date> airDates)
    {
        this.airDates = airDates;
    }

    public void setAuditoriums(NavigableMap<Date, Auditorium> auditoriums)
    {
        this.auditoriums = auditoriums;
    }

    public void setBasePrice(double basePrice)
    {
        this.basePrice = basePrice;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setRating(EventRating rating)
    {
        this.rating = rating;
    }

    @Override
    public String toString()
    {
        return "Event [name=" + name + ", airDates=" + airDates + ", basePrice=" + basePrice + "]";
    }

}
