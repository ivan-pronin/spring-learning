package com.epam.springadvanced.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.springadvanced.entity.User;
import com.epam.springadvanced.repository.CounterRepository;

@Aspect
@Component
public class DiscountAspect
{
    private static final String COLON = ": ";

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountAspect.class);

    @Autowired
    private CounterRepository counterRepository;

    @Pointcut("execution(* com.epam.springadvanced.service.DiscountService.getDiscount(..))")
    public void pointcutDiscount()
    {
    }

    @SuppressWarnings("checkstyle:IllegalThrows")
    @Around("pointcutDiscount()")
    public float count(ProceedingJoinPoint jp) throws Throwable
    {
        float discount = (float) jp.proceed();
        User user = (User) jp.getArgs()[0];
        if (discount != 0 && user != null)
        {
            String counterName = Counters.DISCOUNT_USER_ID.name() + "_" + user.getId();
            int count = counterRepository.getByName(counterName) + 1;
            counterRepository.save(counterName, count);
            LOGGER.info(counterName + COLON + count);

            count = counterRepository.getByName(Counters.DISCOUNT_TOTAL.name()) + 1;
            counterRepository.save(Counters.DISCOUNT_TOTAL.name(), count);
            LOGGER.info(Counters.DISCOUNT_TOTAL + COLON + count);
        }
        return discount;
    }
}
