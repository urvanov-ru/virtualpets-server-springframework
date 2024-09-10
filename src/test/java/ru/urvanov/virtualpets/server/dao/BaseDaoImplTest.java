package ru.urvanov.virtualpets.server.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.urvanov.virtualpets.server.controller.config.ClockConfig;
import ru.urvanov.virtualpets.server.controller.config.DaoTestConfig;
import ru.urvanov.virtualpets.server.controller.config.DataSourceConfig;

/**
 * Базовый класс для тестов слоя DAO
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoTestConfig.class, ClockConfig.class,
        DataSourceConfig.class})
@Testcontainers
@ActiveProfiles({"test", "test-dao"})
@Transactional
class BaseDaoImplTest {

    /**
     * Управляет запуском и остановкой контейнера PostgreSQL.
     * При запуске нескольких тестов контейнер создаётся один раз
     * и переиспользуется последующими тестами.
     */
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer
            = new PostgreSQLContainer<>("postgres:16.1");

}
