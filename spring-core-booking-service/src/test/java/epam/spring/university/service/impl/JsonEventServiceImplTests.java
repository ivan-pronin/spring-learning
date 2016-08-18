package epam.spring.university.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.service.EventService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes=Config.class)
@ContextConfiguration("classpath:spring-core.xml")
public class JsonEventServiceImplTests extends AbstractEventTests
{

    @Autowired
    private EventService eventService;

    @Test
    public void testGetAll()
    {
        Assert.assertEquals(1, eventService.getAll().size());
    }

    @Test
    public void testGetByName()
    {
        Assert.assertNotNull(eventService.getByName("Terminator6"));
    }

    @Test
    public void testGetById()
    {
        Assert.assertNotNull(eventService.getById(1));
    }

    @Test
    public void testGetForDateRange()
    {
        before();
        Assert.assertNotNull(eventService.getForDateRange(createDateFromString("2016-12-24T19:00:06"),
                createDateFromString(getFutureDatePlusOne())));
    }

    @Test
    public void testGetNext()
    {
        before();
        Assert.assertEquals(1, eventService.getNextEvents(createDateFromString(getFutureDatePlusOne())).size());
    }

}
