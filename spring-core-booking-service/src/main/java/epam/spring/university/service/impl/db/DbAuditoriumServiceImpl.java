package epam.spring.university.service.impl.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import epam.spring.university.domain.Auditorium;
import epam.spring.university.service.AuditoriumService;

public class DbAuditoriumServiceImpl implements AuditoriumService
{
    private JdbcTemplate jdbcTemplate;

    public DbAuditoriumServiceImpl(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Set<Auditorium> getAll()
    {
        return null;
    }

    @Override
    public Auditorium getByName(String name)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM auditoriums WHERE name = ?", new Object[] {name},
                new AuditoriumRowMapper());
    }

    public Auditorium getById(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM auditoriums WHERE id = ?", new Object[] {id},
                new AuditoriumRowMapper());
    }

    private class AuditoriumRowMapper implements RowMapper<Auditorium>
    {
        public Auditorium mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            String name = rs.getString("name");
            int numberOfSeats = rs.getInt("numberOfSeats");
            String vipSeats = rs.getString("vipSeats");
            Set<Integer> seats = Arrays.stream(vipSeats.split(",")).map(String::trim).mapToInt(Integer::parseInt)
                    .boxed().collect(Collectors.toSet());
            return new Auditorium(name, numberOfSeats, seats);
        }
    }
}
