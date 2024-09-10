package ru.urvanov.virtualpets.server.controller.config;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile({"test-dao", "test-spring-boot"})
public class ClockConfig {
    /**
     * Настраивает экземпляр Clock, возвращающий всегда 
     * одну и ту же дату и одно и то же время для предсказуемости
     * тестов.
     * @return Экземпляр Clock с фиксированной датой и временем.
     */
    @Bean
    public Clock clock() {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        TemporalAccessor offsetDateTime = ZonedDateTime.of(
                2024, 3, 15, 18, 52, 0, 0, zoneId);
        Instant instant = Instant.from(offsetDateTime );
        return Clock.fixed(instant, zoneId);
    }
}
