package com.epam.springadvanced.repository;

import java.util.Collection;

import com.epam.springadvanced.entity.Event;

public interface EventRepository
{
    Event save(Event event);

    void delete(long id);

    Event getByName(String name);

    Event getById(Long id);

    Collection<Event> getAll();
}
