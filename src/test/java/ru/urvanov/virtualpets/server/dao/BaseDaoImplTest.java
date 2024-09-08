package ru.urvanov.virtualpets.server.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.urvanov.virtualpets.server.test.config.DaoTestConfig;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={DaoTestConfig.class})
@Testcontainers
@ActiveProfiles({"test", "test-dao"})
@Transactional
class BaseDaoImplTest {

    /**
     * TestContainers PostgreSQL контейнер.
     */
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:16.1");
    
    @PersistenceContext
    protected EntityManager em;
    
    @Test
    public void test() {
    }
}
