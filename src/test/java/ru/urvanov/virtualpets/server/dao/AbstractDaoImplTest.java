/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.urvanov.virtualpets.server.test.config.DaoTestConfig;
import ru.urvanov.virtualpets.server.test.listener.DaoTestExecutionListener;


/**
 * @author fedya
 *
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={DaoTestConfig.class})
@Testcontainers
@TestExecutionListeners(value = {DaoTestExecutionListener.class}, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@ActiveProfiles("test")
@Transactional
public class AbstractDaoImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.1")
            .withReuse(true)
            .withDatabaseName("virtualpets");
    
    @PersistenceContext
    protected EntityManager em;
    
    @Test
    public void test() {
    	System.out.println(em);
    }
}
