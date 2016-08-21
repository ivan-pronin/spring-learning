package com.epam.springadvanced;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.epam.springadvanced.config.SpringConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TestQuery
{

    @Autowired
    private JdbcTemplate db;

    @Test
    public void testRoles()
    {
        String findAllUserRolesQuery = "select u.id as user_id, u.name as username, u.password, r.name as role from user U left join roles RS on u.id = rs.user_id left join role R on r.id = rs.role_id";
        String findUserRole = "select u.name as username, r.name as role, password from user U left join roles RS on u.id = rs.user_id "
                + "left join role R on r.id = rs.role_id where u.name = 'admin'";
        String usr = "SELECT name as username, password, 1 as enabled FROM user WHERE name = ?";
        List<Map<String, Object>> result = db.queryForList(usr, new Object[] {"user"});
        // String result = db.queryForObject(usr, new Object[] {"user"}, String.class);

        System.out.println(result);
    }
}
