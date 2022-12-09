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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Nguyễn Thúc Đồng (dongcopper80)
 */
@Setter
@Getter
@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties("spring.datasource-primary.hikari")
public class HikariPrimaryProperties {
    
    private String poolName;

    private int minimumIdle;

    private int maximumPoolSize;

    private int idleTimeout;
    
    private String packagesToScan;
    
    private boolean showSql;
    
    private String ddlAuto;
    
    private String hbm2ddlAuto;
    
    private String hibernateDialect;
    
    private boolean cachePrepStmts;
    private int prepStmtCacheSize;
    private int prepStmtCacheSqlLimit;
    private boolean useServerPrepStmts;
    private boolean initializationFailFast;
    private boolean implicitCachingEnabled;
    private int maxStatements;
}
