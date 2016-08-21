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

import com.epam.springadvanced.config.web.SecurityConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class, SecurityConfiguration.class})
public class SecutiryConfigurationTests
{

    @Autowired
    private JdbcTemplate db;

    @Test
    public void testAuthoritiesByUsernameQuery()
    {
        String query = "select u.name as username, r.name as role, 123 as pass from user U left join roles RS on u.id = rs.user_id "
                + "left join role R on r.id = rs.role_id where u.name = ?";
        UserData data = db.queryForObject(query, new Object[] {"admin"}, userDataMapper());
        Assert.assertEquals("admin", data.username);
        Assert.assertEquals("BOOKING_MANAGER", data.role);
    }
    /*
     * String findUserQuery = "SELECT name as username, password, 1 as enabled FROM user WHERE name = ?";
     * String findAuthority =
     * "select u.name as username, r.name as role from user U left join roles RS on u.id = rs.user_id "
     * + "left join role R on r.id = rs.role_id where u.name = ?";
     * auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(findUserQuery)
     * .authoritiesByUsernameQuery(findAuthority);
     * auth.authenticationProvider(authenticationProvider(auth));
     *
     */

    private class UserData
    {
        private String username;
        private String role;
        private String password;

        public UserData(String username, String role, String password)
        {
            this.username = username;
            this.role = role;
            this.password = password;
        }
    }

    private RowMapper<UserData> userDataMapper()
    {
        return (rs, rowNum) ->
        {
            UserData data = new UserData(rs.getString(1), rs.getString(2), ofNullable(rs.getString(3)).orElseGet(null));
            return data;
        };
    }
}
