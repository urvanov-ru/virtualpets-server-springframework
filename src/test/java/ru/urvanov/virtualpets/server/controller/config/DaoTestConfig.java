package ru.urvanov.virtualpets.server.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * Конфигурация Spring для тестирования слоя DAO отдельно от всех
 * остальных слоёв приложения.
 */
@Configuration
@ImportResource("file:src/main/webapp/WEB-INF/spring/servlet-tx.xml")
@ComponentScan(basePackages = {"ru.urvanov.virtualpets.server.dao"})
@Profile("test-dao")
public class DaoTestConfig {

}
