package com.sasip.config;

import javax.sql.DataSource;

import org.ff4j.FF4j;
import org.ff4j.security.SpringSecurityAuthorisationManager;
import org.ff4j.springjdbc.store.EventRepositorySpringJdbc;
import org.ff4j.springjdbc.store.FeatureStoreSpringJdbc;
import org.ff4j.springjdbc.store.PropertyStoreSpringJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.thymeleaf.templateresolver.TemplateResolver;

import com.sasip.thymeleaf.MyFF4JDialect;

@Configuration
@ConditionalOnClass({ FF4j.class })
@ComponentScan(value = { "org.ff4j.aop", "org.ff4j.spring" })
public class FF4JConfig
{
    @Qualifier("ff4jDataSource")
    @Autowired
    private DataSource dataSource;

    /*
     * @Autowired SpringResourceTemplateResolver srt;
     */

    @Autowired
    SpringSecurityAuthorisationManager ff4jSecMgr;

    ////////////////////////////////////////
    // FF4J CORE & FEATURE STORE CONFIGURATION
    ////////////////////////////////////////

    @Bean
    public FF4j getFF4j()
    {

        FF4j ff4j = new FF4j();// plain ffj

        // Set feature store to be DATA BASE
        ff4j.setFeatureStore(new FeatureStoreSpringJdbc(dataSource));
        ff4j.setPropertiesStore(new PropertyStoreSpringJdbc(dataSource));
        ff4j.setEventRepository(new EventRepositorySpringJdbc(dataSource));

        // Enale audtit and monitoring (Note: works only on invocation of
        // "check" for usage
        ff4j.audit();

        // Security
        ff4j.setAuthorizationsManager(ff4jSecMgr);

        return ff4j;
    }

    ////////////////////////////////////////
    // FF4J THYMELEAF CONFIGURATION
    ////////////////////////////////////////

    /*
     * @Bean public SpringTemplateEngine templateEngine(TemplateResolver
     * templateResolver) { SpringTemplateEngine templateEngine = new
     * SpringTemplateEngine();
     * templateEngine.setTemplateResolver(templateResolver); MyFF4JDialect
     * dialect = new MyFF4JDialect(); dialect.setFF4J(getFF4j());
     * templateEngine.addDialect(dialect); return templateEngine; }
     */

    @Bean
    public MyFF4JDialect myFF4JDialect()
    {
        MyFF4JDialect dialect = new MyFF4JDialect();
        dialect.setFF4J(getFF4j());

        return dialect;
    }

    ////////////////////////////////////////
    // FF4J SECUIRTY CONFIGURATION
    ////////////////////////////////////////

    @Bean
    public SpringSecurityAuthorisationManager ff4SecuirtyAuthManager()
    {

        return new SpringSecurityAuthorisationManager();
    }

}
