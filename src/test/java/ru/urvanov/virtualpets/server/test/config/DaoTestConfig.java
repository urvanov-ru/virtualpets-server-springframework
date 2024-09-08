package ru.urvanov.virtualpets.server.test.config;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@ImportResource("file:src/main/webapp/WEB-INF/spring/servlet-tx.xml")
@ComponentScan(basePackages = {"ru.urvanov.virtualpets.server.dao"})
@Profile("test")
public class DaoTestConfig {

    
    /*@Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:prepare.sql")
                .addScript("classpath:schema.sql").build();
    }*/
    
    @Bean
    public DataSource dataSource() throws IOException {
        BasicDataSource result = new BasicDataSource();
        // result.setDriverClassName("com.mysql.jdbc.Driver");
        //result.setUrl("jdbc:tc:mysql:8.0.33:///virtualpets?TC_INITSCRIPT=schema.sql");
        result.setUrl("jdbc:tc:postgresql:16.1:///databasename?TC_INITSCRIPT=init.sql");
        //ClassPathResource schema = new ClassPathResource("/schema.sql");
        //String schemaContent = "";
        //try (InputStream inputStream = schema.getInputStream()) {
        //    schemaContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
       // }
        
        //result.setConnectionInitSqls(List.of("GRANT ALL PRIVILEGES ON *.* TO 'test'@'%';"));
        //result.setUsername("root");
        //result.setPassword("");
        result.setDefaultSchema("virtualpets_server_springframework");
        return result;
    }

    @Bean(name = "databaseTester")
    public DataSourceDatabaseTester dataSourceDatabaseTester() throws Exception {
        DataSourceDatabaseTester databaseTester = new DataSourceDatabaseTester(
                dataSource()) {
            public IDatabaseConnection getConnection() throws Exception {
                IDatabaseConnection result = super.getConnection();
                result.getConfig().setProperty(DatabaseConfig.PROPERTY_ESCAPE_PATTERN, "\"?\"");
                result.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
                return result;
            }
        };
        return databaseTester;
    }

    @Bean(name = "xlsDataFileLoader")
    public XlsDataFileLoader xlsDataFileLoader() {
        return new XlsDataFileLoader();
    }
    
    @Bean
    public Clock clock() {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        TemporalAccessor offsetDateTime = ZonedDateTime.of(2024, 3, 15, 18, 52, 0, 0, zoneId);
        Instant instant = Instant.from(offsetDateTime );
        return Clock.fixed(instant, zoneId);
    }
}
