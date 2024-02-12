/**
 * 
 */
package ru.urvanov.virtualpets.server.test.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Driver;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @author fedya
 * 
 */
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
        result.setDefaultSchema("virtualpets");
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
}
