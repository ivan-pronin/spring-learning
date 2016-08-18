package epam.spring.university;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan
@PropertySource("classpath:app.properties")
@EnableAspectJAutoProxy
public class Config
{
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "strategies")
    public Set<String> getDiscountStrategies()
    {
        Set<String> strategies = new HashSet<>();
        strategies.add("birthdayDiscountStrategy");
        strategies.add("datetimeDiscountStrategy");
        strategies.add("volumeDiscountStrategy");
        return strategies;
    }
}
