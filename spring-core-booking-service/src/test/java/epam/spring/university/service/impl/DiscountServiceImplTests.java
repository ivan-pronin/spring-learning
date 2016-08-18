package epam.spring.university.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import epam.spring.university.domain.User;
import epam.spring.university.service.DiscountService;
import epam.spring.university.service.discount.DiscountType;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes=Config.class)
@ContextConfiguration("classpath:spring-core.xml")
public class DiscountServiceImplTests extends AbstractEventTests
{
    @Autowired
    private DiscountService discountService;

    private static final String EARLY_TIME = "2016-12-25T12:00:00";

    @Override
    @Before
    public void before()
    {
        super.before();
    }

    @Test
    public void testGetDiscountOnBirthday()
    {
        User bornUser = new User();
        bornUser.setBirthDate(createDateFromString(getFutureDatePlusOne()));
        Assert.assertEquals(DiscountType.BIRTHDAY.getDiscount(),
                discountService.getDiscount(bornUser, getEvent(), getEventDate(), 1), 0.1);
    }

    @Test
    public void testGetDiscountDatetime()
    {
        Assert.assertEquals(DiscountType.DATETIME.getDiscount(),
                discountService.getDiscount(getUser(), getEvent(), createDateFromString(EARLY_TIME), 1), 0.1);
    }

    @Test
    public void testGetDiscountVolume()
    {
        Assert.assertEquals(DiscountType.VOLUME.getDiscount() / 10,
                discountService.getDiscount(getUser(), getEvent(), getEventDate(), 10), 0.1);
    }

    @Test
    public void testGetDiscountNoDiscount()
    {
        Assert.assertEquals(0, discountService.getDiscount(getUser(), getEvent(), getEventDate(), 1), 0.1);
    }
}
