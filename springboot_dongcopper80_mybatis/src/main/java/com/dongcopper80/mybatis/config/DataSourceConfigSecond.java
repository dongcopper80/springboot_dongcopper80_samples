/*
 * The MIT License
 *
 * Copyright 2022 Nguyễn Thúc Đồng (dongcopper80).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dongcopper80.mybatis.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */
@Configuration
@ConfigurationProperties("spring.datasource-second")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactorySecond",
        transactionManagerRef = "transactionManagerSecond",
        basePackages = "com.dongcopper80.mybatis.repository.second"
)
public class DataSourceConfigSecond extends HikariConfigSecond {
    
    @Autowired
    Environment environment;
    
    public DataSourceConfigSecond(HikariSecondProperties hikariSecondProperties) {
        super(hikariSecondProperties);
    }
    
    @Bean
    public HikariDataSource dataSourceSecond() {
        return new HikariDataSource(this);
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactorySecond(final HikariDataSource dataSourceSecond) {

        return new LocalContainerEntityManagerFactoryBean() {{
            setDataSource(dataSourceSecond);
            setPersistenceProviderClass(HibernatePersistenceProvider.class);
            setPersistenceUnitName(hikariSecondProperties.getPoolName());
            setPackagesToScan(hikariSecondProperties.getPackagesToScan());
            setJpaProperties(jpaProperties());
            //setJpaVendorAdapter(jpaVendorAdapter());
            //setJpaDialect(jpaDialect());
            //setPackagesToScan("net.jkratz.bloodpressure.api.model");
        }};
    }
    
    private Properties jpaProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hikariSecondProperties.getHibernateDialect());
        properties.put("hibernate.hbm2ddl.auto", hikariSecondProperties.getHbm2ddlAuto());
        properties.put("hibernate.ddl-auto", hikariSecondProperties.getDdlAuto());
        properties.put("show-sql", hikariSecondProperties.isShowSql());
        return properties;
    }
    
    @Bean
    public PlatformTransactionManager transactionManagerSecond(EntityManagerFactory entityManagerFactorySecond) {
        return new JpaTransactionManager(entityManagerFactorySecond);
    }
}
