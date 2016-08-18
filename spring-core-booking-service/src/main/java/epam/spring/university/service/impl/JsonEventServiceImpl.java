package epam.spring.university.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

import epam.spring.university.domain.Event;
import epam.spring.university.service.EventService;
import epam.spring.university.util.IJsonUtils;
import epam.spring.university.util.IResourceUtils;

public class JsonEventServiceImpl extends AbstractJsonServiceImpl implements EventService
{
    private Set<Event> events = new HashSet<>();

    @SuppressWarnings("unused")
    private JsonEventServiceImpl()
    {
        super();
    }

    protected JsonEventServiceImpl(@Value("${eventDataFileName}") String dataFileName, IResourceUtils resourceUtils,
            IJsonUtils jsonUtils, @Value("epam.spring.university.domain.Event") Class<?> domainClazz)
    {
        super(dataFileName, resourceUtils, jsonUtils, domainClazz);
    }

    @Override
    protected void init()
    {
        events = super.getAll();
    }

    @Override
    public Event save(Event object)
    {
        if (events.add(object))
        {
            return object;
        }
        return null;
    }

    @Override
    public void remove(Event object)
    {
        events.remove(object);
    }

    @Override
    public Event getById(int id)
    {
        Set<Event> results = events.stream().filter(e -> e.getId() == id).collect(Collectors.toSet());
        return results.isEmpty() ? null : results.iterator().next();
    }

    @Override
    public Event getByName(String name)
    {
        Set<Event> results = events.stream().filter(e -> e.getName().contains(name)).collect(Collectors.toSet());
        return results.isEmpty() ? null : results.iterator().next();
    }

    @Override
    public Set<Event> getForDateRange(Date from, Date to)
    {
        return events.stream().filter(e -> !e.getAirDates().subSet(from, to).isEmpty()).collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getNextEvents(Date to)
    {
        return events.stream().filter(e -> !e.getAirDates().headSet(to, true).isEmpty()).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Event> getAll()
    {
        return events;
    }
}
