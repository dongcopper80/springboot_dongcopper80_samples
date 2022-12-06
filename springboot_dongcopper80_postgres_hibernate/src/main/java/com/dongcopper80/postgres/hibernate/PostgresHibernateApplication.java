package com.dongcopper80.postgres.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@EnableJpaRepositories(basePackages = "com.dongcopper80.postgres.hibernate.repositories",
        entityManagerFactoryRef = "sessionFactory",
        transactionManagerRef = "txManager")
@ComponentScan(basePackages = {"com.dongcopper80.postgres.hibernate"})
@PropertySource({
    "classpath:application.properties"
})
@EnableJpaAuditing
@Slf4j
public class PostgresHibernateApplication {

    private static Logger logger = LoggerFactory.getLogger(PostgresHibernateApplication.class);

    @Autowired
    private Environment env;

    public static void main(String[] args) {

        Properties prop = new Properties();
        InputStream input = null;
        String username = null;
        String password = null;
        String datname = null;

        try {
            ClassLoader classLoader = PostgresHibernateApplication.class.getClassLoader();
            input = classLoader.getResourceAsStream("application.properties");
            prop.load(input);
            username = prop.getProperty("spring.datasource.username");
            password = prop.getProperty("spring.datasource.password");
            datname = prop.getProperty("spring.datasource.datname");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

        }

        Connection connection = null;
        Statement statement = null;
        try {

            logger.debug("Creating database if not exist...");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", username, password);
            statement = connection.createStatement();
            statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = '" + datname + "'");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count <= 0) {
                statement.executeUpdate("CREATE DATABASE " + datname);
                logger.debug("Database created.");
            } else {
                logger.debug("Database already exist.");
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    logger.debug("Closed Statement.");
                }
                if (connection != null) {
                    logger.debug("Closed Connection.");
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.toString());
            }
        }

        SpringApplication.run(PostgresHibernateApplication.class, args);
    }
}
