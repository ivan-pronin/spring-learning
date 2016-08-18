package epam.spring.university.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.domain.User;
import epam.spring.university.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes=Config.class)
@ContextConfiguration("classpath:spring-core.xml")
public class JsonUserServiceImplTests
{

    @Autowired
    private UserService userService;

    @Test
    public void testGetAll()
    {
        Assert.assertEquals(2, userService.getAll().size());
    }

    @Test
    public void testGetById()
    {
        Assert.assertNotNull(userService.getById(2));
    }

    @Test
    public void testGetByEmail()
    {
        Assert.assertNotNull(userService.getUserByEmail("johnsmith@mail.com"));
    }

    @Test
    public void testSave()
    {
        User newUser = new User();
        userService.save(newUser);
        Assert.assertEquals(3, userService.getAll().size());
        userService.remove(newUser);
    }

    @Test
    public void testRemove()
    {
        User userToRemove = userService.getById(2);
        userService.remove(userToRemove);
        Assert.assertEquals(1, userService.getAll().size());
        userService.save(userToRemove);
    }
}
