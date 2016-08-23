package com.epam.springadvanced.config;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@Configuration
@ComponentScan("com.epam.springadvanced.repository")
public class DataConfiguration
{
    @Bean
    public DataSource dataSource()
    {
        return new EmbeddedDatabaseBuilder().setType(HSQL).addScript("classpath:db/create_tables.sql").build();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate()
    {
        return new JdbcTemplate(dataSource());
    }
}
