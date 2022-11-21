package com.dongcopper80.postgres.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { 
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class, 
    HibernateJpaAutoConfiguration.class 
})
@EnableJpaRepositories(basePackages="com.dongcopper80.postgres.hibernate.repositories", 
        entityManagerFactoryRef = "sessionFactory", 
        transactionManagerRef = "txManager")
@ComponentScan(basePackages={"com.dongcopper80.postgres.hibernate"})
@EnableJpaAuditing
@Slf4j
public class PostgresHibernateApplication 
{
    public static void main( String[] args )
    {
        SpringApplication.run(PostgresHibernateApplication.class, args);
    }
}
