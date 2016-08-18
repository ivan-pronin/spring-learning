package epam.spring.university.aspect;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import epam.spring.university.domain.User;

@Aspect
@Component
public class DiscountCounterAspect
{
    private Map<User, Map<String, Integer>> discountCounter = new HashMap<>();

    @AfterReturning(pointcut = "onGetDiscount()", returning = "discount")
    public synchronized void countGetName(JoinPoint joinPoint, double discount)
    {
        if (Double.compare(discount, 0.0) > 0)
        {
            User user = (User) joinPoint.getArgs()[0];
            Map<String, Integer> userRecords = discountCounter.get(user);
            String key = joinPoint.getTarget().getClass().getName();
            if (userRecords != null)
            {
                Integer counter = userRecords.get(key);
                if (counter != null)
                {
                    userRecords.put(key, ++counter);
                }
                else
                {
                    userRecords.put(key, 1);
                }
            }
            else
            {
                userRecords = new HashMap<>();
                userRecords.put(key, 1);
            }
            discountCounter.put(user, userRecords);
        }
    }

    @Pointcut("execution(* *getDiscount(..)) && within(epam.spring.university.service.discount.*)")
    public void onGetDiscount()
    {
    }

    public Map<User, Map<String, Integer>> getDiscountCounter()
    {
        return discountCounter;
    }
}
