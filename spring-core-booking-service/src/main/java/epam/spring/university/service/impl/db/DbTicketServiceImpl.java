package epam.spring.university.service.impl.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import epam.spring.university.domain.Event;
import epam.spring.university.domain.Ticket;
import epam.spring.university.domain.User;
import epam.spring.university.service.TicketService;

public class DbTicketServiceImpl implements TicketService
{
    private JdbcTemplate jdbcTemplate;
    private DbDatesService dbDatesService;
    private DbConfigurer dbConfigurer;
    private DbUserServiceImpl dbUserService;
    private DbEventServiceImpl dbEventService;

    public DbTicketServiceImpl(JdbcTemplate jdbcTemplate, DbDatesService dbDatesService, DbConfigurer dbConfigurer,
            DbUserServiceImpl dbUserService, DbEventServiceImpl dbEventService)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.dbDatesService = dbDatesService;
        this.dbConfigurer = dbConfigurer;
        this.dbUserService = dbUserService;
        this.dbEventService = dbEventService;
    }

    @Override
    public Set<Ticket> getAll()
    {
        List<Ticket> tickets = jdbcTemplate.query("SELECT * FROM tickets", new TicketRowMapper());
        return new HashSet<>(tickets);
    }

    @Override
    public Ticket getById(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM tickets WHERE id = ?", new Object[] {id},
                new TicketRowMapper());
    }

    private class TicketRowMapper implements RowMapper<Ticket>
    {
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            int userId = rs.getInt("userId");
            int eventId = rs.getInt("eventId");
            int dateId = rs.getInt("dateId");
            int seat = rs.getInt("seat");
            User user = dbUserService.getById(userId);
            Event event = dbEventService.getById(eventId);
            Date date = dbDatesService.getById(dateId);
            return new Ticket(user, event, date, seat);
        }
    }

    @Override
    public Set<Ticket> getTicketsByEvent(Event event)
    {
        int eventId = event.getId();
        List<Ticket> tickets = jdbcTemplate.query("SELECT * FROM tickets WHERE eventId = ?", new Object[] {eventId},
                new TicketRowMapper());
        return new HashSet<>(tickets);
    }

    @Override
    public Set<Ticket> getTicketsByUser(User user)
    {
        int userId = user.getId();
        List<Ticket> tickets = jdbcTemplate.query("SELECT * FROM tickets WHERE userId = ?", new Object[] {userId},
                new TicketRowMapper());
        return new HashSet<>(tickets);
    }

    @Override
    public void remove(Ticket object)
    {
        jdbcTemplate.update("DELETE FROM tickets WHERE id= ?", object.getId());
    }

    @Override
    public void removeAllTickets()
    {
        dbConfigurer.truncateTable("tickets");
    }

    @Override
    public Ticket save(Ticket object)
    {
        int rowsAffected = jdbcTemplate.update("INSERT INTO tickets(userId, eventId, dateId, seat) VALUES(?,?,?,?)",
                object.getUser().getId(), object.getEvent().getId(), dbDatesService.getDateId(object.getDateTime()),
                object.getSeat());
        if (rowsAffected > 0)
        {
            return object;
        }
        return null;
    }

}
