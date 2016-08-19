package com.epam.springadvanced.repository.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.epam.springadvanced.entity.Event;
import com.epam.springadvanced.entity.Ticket;
import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.AuditoriumRepository;
import com.epam.springadvanced.repository.EventRepository;
import com.epam.springadvanced.repository.TicketRepository;
import com.epam.springadvanced.service.Rating;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private static final String SELECT_ALL = "SELECT t.*, e.* FROM ticket t\n" +
            "LEFT JOIN event e ON t.event_id = e.id";
    private static final String SELECT_BY_EVENT_NAME = "SELECT t.*, e.* FROM ticket t\n" +
            "INNER JOIN event e ON t.event_id = e.id\n" +
            "WHERE e.name = ?";
    private static final String UPDATE_TICKET = "UPDATE ticket SET price = ?, seat = ?, event_id = ?";
    private static final String SELECT_BOOKED_TICKETS = "SELECT t.*, e.* FROM ticket t\n" +
            "INNER JOIN tickets ts ON ts.ticket_id = t.id\n" +
            "LEFT JOIN user u ON ts.user_id = u.id\n" +
            "LEFT JOIN event e ON t.event_id = e.id";
    private static final String SELECT_BOOKED_TICKETS_BY_USER_ID = "SELECT t.*, e.* FROM ticket t\n" +
            "INNER JOIN tickets ts ON ts.ticket_id = t.id\n" +
            "LEFT JOIN user u ON ts.user_id = u.id\n" +
            "LEFT JOIN event e ON t.event_id = e.id\n" +
            "WHERE u.id = ?";
    private static final String INSERT_INTO_TICKETS = "INSERT INTO tickets(user_id, ticket_id) VALUES (?,?)";
    private static final String DELETE_BOOKED_TICKETS = "DELETE FROM TICKETS WHERE USER_ID = ?";

    @Autowired
    private AuditoriumRepository auditoriumRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Ticket save(Ticket ticket) {
        if (ticket != null) {
            if (ticket.getId() == null) {
                SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("ticket");
                insert.setGeneratedKeyName("id");
                Map<String, Object> args = new HashMap<>();
                args.put("price", ticket.getPrice());
                args.put("seat", ticket.getSeat().getNumber());
                Event event = ticket.getEvent();
                if (event != null) {
                    if (event.getId() == null) {
                        event = eventRepository.getByName(ticket.getEvent().getName());
                    }
                    args.put("event_id", event.getId());
                }
                ticket.setId(insert.executeAndReturnKey(args).longValue());
            } else {
                jdbcTemplate.update(UPDATE_TICKET,
                        ticket.getPrice(),
                        ticket.getSeat().getNumber(),
                        ticket.getEvent() != null ? ticket.getEvent().getId() : null);
            }
        }
        return ticket;
    }

    @Override
    public Collection<Ticket> getAll() {
        return jdbcTemplate.query(SELECT_ALL, ticketMapper());
    }

    @Override
    public Collection<Ticket> getByEventName(String eventName) {
        return jdbcTemplate.query(SELECT_BY_EVENT_NAME, ticketMapper(), eventName);
    }

    @Override
    public Collection<Ticket> getBookedTickets() {
        return jdbcTemplate.query(SELECT_BOOKED_TICKETS, ticketMapper());
    }

    @Override
    public Collection<Ticket> getBookedTicketsByUserId(long userId) {
        return jdbcTemplate.query(SELECT_BOOKED_TICKETS_BY_USER_ID, ticketMapper(), userId);
    }

    @Override
    public void saveBookedTicket(User user, Ticket ticket) {
        if (user != null && user.getId() != null && ticket != null) {
            if (ticket.getId() == null) {
                ticket = save(ticket);
            }
            jdbcTemplate.update(INSERT_INTO_TICKETS, user.getId(), ticket.getId());
        }
    }

    @Override
    public void deleteBookedTicketByUserId(long userId) {
        jdbcTemplate.update(DELETE_BOOKED_TICKETS, userId);
    }

    private RowMapper<Ticket> ticketMapper() {
        return (rs, rowNum) -> {
            Ticket t = new Ticket();
            t.setId(rs.getLong(1));
            t.setPrice(rs.getFloat(2));
            Event e = new Event(
                    rs.getLong(5),
                    rs.getString(6),
                    rs.getTimestamp(7) != null ? rs.getTimestamp(7).toLocalDateTime() : null,
                    rs.getFloat(8),
                    Rating.getRating(rs.getInt(9))
            );
            e.setAuditorium(auditoriumRepository.getById(rs.getInt(10)));
            t.setSeat(e.getAuditorium().getSeats().get(rs.getInt(3) - 1));
            t.setEvent(e);
            return t;
        };
    }
}
