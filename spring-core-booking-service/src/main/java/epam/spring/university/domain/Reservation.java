package epam.spring.university.domain;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class used to track information about vacant seats in the specified auditorium, event, datetime
 * @author Ivan_Pronin
 */
public class Reservation
{
    private Event event;
    private Date eventDateTime;
    private Set<Integer> vacantSeats;

    public Reservation(Event event, Date eventDateTime, int totalNumberOfSeats)
    {
        this.event = event;
        this.eventDateTime = eventDateTime;
        vacantSeats = IntStream.rangeClosed(1, totalNumberOfSeats).boxed().collect(Collectors.toSet());
    }

    public Set<Integer> getVacantSeats()
    {
        return vacantSeats;
    }

    public Event getEvent()
    {
        return event;
    }

    public Date getEventDateTime()
    {
        return eventDateTime;
    }
}
