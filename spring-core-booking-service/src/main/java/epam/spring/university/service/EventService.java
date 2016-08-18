package epam.spring.university.service;

import java.util.Date;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import epam.spring.university.domain.Event;

/**
 * @author Yuriy_Tkach
 */
public interface EventService extends AbstractDomainObjectService<Event>
{

    /**
     * Finding event by name
     * @param name
     * Name of the event
     * @return found event or <code>null</code>
     */
    public @Nullable Event getByName(@Nonnull String name);

    /**
     * Finding all events that air on specified date range
     * @param from Start date
     * @param to End date inclusive
     * @return Set of events
     */
    public @Nonnull Set<Event> getForDateRange(@Nonnull Date from, @Nonnull Date to);

    /**
     * Return events from 'now' till the the specified date time
     * @param to End date time inclusive
     * s
     * @return Set of events
     */
    public @Nonnull Set<Event> getNextEvents(@Nonnull Date to);

}
