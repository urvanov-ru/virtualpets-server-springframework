package ru.urvanov.virtualpets.server.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

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
     * Настройка источника данных на базу данных
     * PostgreSQL в контейнере Testcontainers.
     * @return Источник данных для тестов.
     * @throws SQLException 
     */
    @Bean(destroyMethod = "close")
    public DataSource dataSource() throws IOException, SQLException {
        // DataSource с одним всегда работающим подключением.
        // Фактически это не пул подключений, он всегда
        // возвращает одно и то же подключение к базе данных,
        // не создавая новое.
        SingleConnectionDataSource result = new SingleConnectionDataSource();
        
        // jdbc:tc ... - это адрес к базе данных в контейнере
        // Адрес взят из документации Testcontainers.
        result.setUrl("""
                jdbc:tc:postgresql:16.1:///databasename?\
                TC_INITSCRIPT=init.sql""");
        
        // ContainerDatabaseDriver работает с подключениями вида
        // jdbc:tc
        result.setDriverClassName(
                "org.testcontainers.jdbc.ContainerDatabaseDriver");
        
        // Рабочая схема с таблицами проекта.
        result.setSchema("virtualpets_server_springboot");
        
        // Заглушаем вызовы close в возвращаемом подключении, 
        // чтобы никто не закрывал наше единственное подключение.
        // В реальности это оборачивает возвращаемый Connection
        // в прокси, в котором вызовы close игнорируются.
        result.setSuppressClose(true);
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
