package com.epam.springadvanced.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.epam.springadvanced.config.web.SecurityConfiguration;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@Configuration
@Import({AspectConfiguration.class, DataConfiguration.class, AuditoriumConfiguration.class,
        SecurityConfiguration.class})
@PropertySource("classpath:auditorium1.properties")
@PropertySource("classpath:auditorium2.properties")
@PropertySource("classpath:auditorium3.properties")
@ComponentScan({"com.epam.springadvanced.service", "com.epam.springadvanced.facade"})
public class SpringConfiguration
{
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
