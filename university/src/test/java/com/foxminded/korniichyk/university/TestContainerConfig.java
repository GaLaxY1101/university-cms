package com.foxminded.korniichyk.university;

import org.flywaydb.core.Flyway;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@TestConfiguration
@Testcontainers
public class TestContainerConfig {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("university-test")
            .withUsername("test")
            .withPassword("test");

    static {
        postgreSQLContainer.start();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgreSQLContainer.getDriverClassName());
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());
        return dataSource;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load();
    }
}
