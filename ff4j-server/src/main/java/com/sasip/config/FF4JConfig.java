package com.sasip.config;

import javax.sql.DataSource;

import org.ff4j.FF4j;
import org.ff4j.springjdbc.store.EventRepositorySpringJdbc;
import org.ff4j.springjdbc.store.FeatureStoreSpringJdbc;
import org.ff4j.springjdbc.store.PropertyStoreSpringJdbc;
import org.ff4j.web.FF4jDispatcherServlet;
import org.ff4j.web.embedded.ConsoleServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ ConsoleServlet.class, FF4jDispatcherServlet.class })
public class FF4JConfig extends SpringBootServletInitializer
{

    @Autowired
    private DataSource dataSource;

    @Bean
    public ServletRegistrationBean<FF4jDispatcherServlet> ff4jDispatcherServletRegistrationBean(FF4jDispatcherServlet ff4jDispatcherServlet)
    {
        ServletRegistrationBean<FF4jDispatcherServlet> bean = new ServletRegistrationBean<FF4jDispatcherServlet>(ff4jDispatcherServlet, "/demo-features/*");
        bean.setName("ff4j-console");
        bean.setLoadOnStartup(1);
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public FF4jDispatcherServlet getFF4jDispatcherServlet()
    {
        FF4jDispatcherServlet ff4jConsoleServlet = new FF4jDispatcherServlet();
        ff4jConsoleServlet.setFf4j(getFF4j());
        return ff4jConsoleServlet;
    }

    @Bean
    public FF4j getFF4j()
    {
        FF4j ff4j = new FF4j();
        ff4j.setFeatureStore(new FeatureStoreSpringJdbc(dataSource));
        ff4j.setPropertiesStore(new PropertyStoreSpringJdbc(dataSource));
        ff4j.setEventRepository(new EventRepositorySpringJdbc(dataSource));

        // Audit capabilities
        ff4j.audit(true);

        return ff4j;
    }

}
