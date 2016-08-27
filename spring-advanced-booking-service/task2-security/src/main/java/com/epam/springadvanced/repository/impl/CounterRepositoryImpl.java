package com.epam.springadvanced.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.epam.springadvanced.repository.CounterRepository;

@Repository
public class CounterRepositoryImpl implements CounterRepository
{
    private static final String UPDATE_COUNTER = "UPDATE counters SET counter=? WHERE name=?";
    private static final String INSERT_COUNTER = "INSERT INTO counters(name, counter) VALUES (?,?)";
    private static final String SELECT_BY_NAME = "SELECT counter FROM counters WHERE name=?";
    private static final String INCREMENT_COUNTER = "UPDATE counters SET counter = counter+1 WHERE name=?";

    private static final Logger LOGGER = LoggerFactory.getLogger(CounterRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(String counterName, int count)
    {
        if (counterName != null && !counterName.isEmpty())
        {
            if (jdbcTemplate.update(UPDATE_COUNTER, count, counterName) == 0)
            {
                jdbcTemplate.update(INSERT_COUNTER, counterName, count);
            }
        }
    }

    @Override
    public int getByName(String counterName)
    {
        try
        {
            return jdbcTemplate.queryForObject(SELECT_BY_NAME, Integer.class, counterName);
        }
        catch (EmptyResultDataAccessException ignored)
        {
            LOGGER.warn("No counter found with name: {}", counterName);
        }
        return 0;
    }

    @Override
    public void increment(String counterName)
    {
        jdbcTemplate.update(INCREMENT_COUNTER, counterName);
    }
}
