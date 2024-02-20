package ru.urvanov.virtualpets.server.example;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ImportResource("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class AbstractExampleConfig {


    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb;DATABASE_TO_UPPER=false;MODE=PostgreSQL")
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("init.sql")
                .build();
    }

}
