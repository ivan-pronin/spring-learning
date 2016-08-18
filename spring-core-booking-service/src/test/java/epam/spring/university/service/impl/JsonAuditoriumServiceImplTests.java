package epam.spring.university.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.service.AuditoriumService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes=Config.class)
@ContextConfiguration("classpath:spring-core.xml")
public class JsonAuditoriumServiceImplTests
{

    @Autowired
    private AuditoriumService auditoriumService;

    @Test
    public void testGetAll()
    {
        Assert.assertEquals(1, auditoriumService.getAll().size());
    }

    @Test
    public void testGetByName()
    {
        Assert.assertNotNull(auditoriumService.getByName("MoscowVision"));
    }
}
