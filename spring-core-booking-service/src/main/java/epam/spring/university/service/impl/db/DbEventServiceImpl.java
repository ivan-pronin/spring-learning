package epam.spring.university.service.impl.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import epam.spring.university.domain.Auditorium;
import epam.spring.university.domain.Event;
import epam.spring.university.service.EventService;

public class DbEventServiceImpl implements EventService
{
    private JdbcTemplate jdbcTemplate;
    private DbDatesService dbDatesService;
    private DbRatingService dbRatingService;
    private DbAuditoriumServiceImpl dbAuditoriumService;

    public DbEventServiceImpl(JdbcTemplate jdbcTemplate, DbDatesService dbDatesService, DbRatingService dbRatingService,
            DbAuditoriumServiceImpl dbAuditoriumService)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.dbDatesService = dbDatesService;
        this.dbRatingService = dbRatingService;
        this.dbAuditoriumService = dbAuditoriumService;
    }

    private class EventRowMapper implements RowMapper<Event>
    {
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            String name = rs.getString("name");
            int dateId = rs.getInt("dateId");
            int ratingId = rs.getInt("ratingId");
            double basePrice = rs.getDouble("basePrice");
            Date date = dbDatesService.getById(dateId);
            NavigableMap<Date, Auditorium> map = new TreeMap<>();
            map.put(date, dbAuditoriumService.getById(1));
            return new Event(name, new TreeSet<Date>(Arrays.asList(date)), basePrice, dbRatingService.getById(ratingId),
                    map);
        }
    }

    @Override
    public Set<Event> getAll()
    {
        List<Event> events = jdbcTemplate.query("SELECT * FROM events", new EventRowMapper());
        return new HashSet<>(events);
    }

    @Override
    public Event getById(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM events WHERE id = ?", new Object[] {id},
                new EventRowMapper());
    }

    @Override
    public Event getByName(String name)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM events WHERE name = ?", new Object[] {name},
                new EventRowMapper());
    }

    @Override
    public Set<Event> getForDateRange(Date from, Date to)
    {
        return null;
    }

    @Override
    public Set<Event> getNextEvents(Date to)
    {
        return null;
    }

    @Override
    public void remove(Event object)
    {
        jdbcTemplate.update("DELETE FROM events WHERE id= ?", object.getId());
    }

    @Override
    public Event save(Event object)
    {
        int rowsAffected = jdbcTemplate.update(
                "INSERT INTO events(name, dateId, ratingId, auditoriumId) VALUES(?,?,?,?)", object.getName(),
                dbDatesService.getDateId(object.getAirDates().iterator().next()),
                dbRatingService.getId(object.getRating()), 1);
        if (rowsAffected > 0)
        {
            return object;
        }
        return null;
    }

}
