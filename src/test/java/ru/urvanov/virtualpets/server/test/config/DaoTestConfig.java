package ru.urvanov.virtualpets.server.test.config;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@ImportResource("file:src/main/webapp/WEB-INF/spring/servlet-tx.xml")
@ComponentScan(basePackages = {"ru.urvanov.virtualpets.server.dao"})
@Profile("test")
public class DaoTestConfig {

    @Bean
    public DataSource dataSource() throws IOException {
        BasicDataSource result = new BasicDataSource();
        result.setUrl("jdbc:tc:postgresql:16.1:///databasename?TC_INITSCRIPT=init.sql");
        result.setDefaultSchema("virtualpets_server_springframework");
        return result;
    }

    @Bean
    @Primary
    public Clock fixedClock() {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        TemporalAccessor offsetDateTime = ZonedDateTime.of(
                2024, 3, 15, 18, 52, 0, 0, zoneId);
        Instant instant = Instant.from(offsetDateTime );
        return Clock.fixed(instant, zoneId);
    }
}
