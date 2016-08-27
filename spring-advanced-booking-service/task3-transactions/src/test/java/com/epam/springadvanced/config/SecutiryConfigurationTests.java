package com.epam.springadvanced.config;

import static java.util.Optional.ofNullable;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class SecutiryConfigurationTests
{
    private static final String ADMIN = "admin";
    @Autowired
    private JdbcTemplate db;

    @Test
    public void testAuthoritiesByUsernameQuery()
    {
        String query = "select u.name as username, r.name as role, 123 as pass from user U left join roles RS on u.id "
                + "= rs.user_id left join role R on r.id = rs.role_id where u.name = ?";
        UserData data = db.queryForObject(query, new Object[] {ADMIN}, userDataMapper());
        Assert.assertEquals(ADMIN, data.username);
        Assert.assertEquals("BOOKING_MANAGER", data.role);
    }

    private RowMapper<UserData> userDataMapper()
    {
        return (rs, rowNum) ->
        {
            UserData data = new UserData(rs.getString(1), rs.getString(2), ofNullable(rs.getString(3)).orElseGet(null));
            return data;
        };
    }

    private final class UserData
    {
        private String username;
        private String role;
        private String password;

        private UserData(String username, String role, String password)
        {
            this.username = username;
            this.role = role;
            this.password = password;
        }
    }
}
