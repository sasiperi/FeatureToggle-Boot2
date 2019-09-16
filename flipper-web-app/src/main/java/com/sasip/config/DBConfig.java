package com.sasip.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DBConfig
{

    @Bean(name = "datasource")
    @ConfigurationProperties("spring.datasource")
    @Primary
    public DataSource dataSource()
    {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ff4jDataSource")
    @ConfigurationProperties("ff4j.datasource")
    public DataSource ff4jDataSource()
    {
        return DataSourceBuilder.create().build();
    }

}
