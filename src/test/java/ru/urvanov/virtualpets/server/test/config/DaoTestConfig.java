package ru.urvanov.virtualpets.server.test.config;

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

/**
 * Конфигурация Spring для тестирования слоя DAO отдельно от всех
 * остальных слоёв приложения.
 */
@Configuration
@ImportResource("file:src/main/webapp/WEB-INF/spring/servlet-tx.xml")
@ComponentScan(basePackages = {"ru.urvanov.virtualpets.server.dao"})
@Profile("test")
public class DaoTestConfig {

    /**
     * Настраивает источник данных, подключающийся к базе данных
     * PostgreSQL в контейнере Testcontainers.
     * @return Источник данных для тестов.
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource result = new BasicDataSource();
        result.setUrl("""
                jdbc:tc:postgresql:16.1:///databasename?\
                TC_INITSCRIPT=init.sql""");
        result.setDefaultSchema("virtualpets_server_springframework");
        return result;
    }

    /**
     * Настраивает экземпляр Clock, возвращающий всегда 
     * одну и ту же дату и одно и то же время для предсказуемости
     * тестов.
     * @return Экземпляр Clock с фиксированной датой и временем.
     */
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
