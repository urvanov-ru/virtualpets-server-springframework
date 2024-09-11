package ru.urvanov.virtualpets.server.controller.config;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@Configuration
@Profile({"test-dao", "test-mock-mvc"})
public class DataSourceConfig {

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
        SingleConnectionDataSource result
                = new SingleConnectionDataSource();
        
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

}
