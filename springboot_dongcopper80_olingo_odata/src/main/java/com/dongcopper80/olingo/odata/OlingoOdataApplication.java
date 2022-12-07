package com.dongcopper80.olingo.odata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
public class OlingoOdataApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OlingoOdataApplication.class, args);
    }
}
