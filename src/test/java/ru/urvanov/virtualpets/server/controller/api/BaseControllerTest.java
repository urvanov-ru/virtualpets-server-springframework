package ru.urvanov.virtualpets.server.controller.api;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.urvanov.virtualpets.server.test.config.MockMvcConfig;

@ExtendWith(SpringExtension.class)
@Testcontainers
@ActiveProfiles({"test", "test-spring-boot"})
@WebAppConfiguration("classpath:src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(classes = { MockMvcConfig.class }),
        @ContextConfiguration(locations = {
                "file:src/main/webapp/WEB-INF/spring/root-context.xml",
                "file:src/main/webapp/WEB-INF/spring/security.xml",
                "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" }) })
abstract class BaseControllerTest {
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            "postgres:16.1");

    @PersistenceContext
    protected EntityManager em;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }
}
