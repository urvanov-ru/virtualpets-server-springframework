package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;

@Sql("/ru/urvanov/virtualpets/server/clean.sql")
class RefrigeratorDaoImplTest extends BaseDaoImplTest {

    @Autowired
    private RefrigeratorDao refrigeratorDao;

    @Test
    void testFind1() {
        Refrigerator refrigerator = refrigeratorDao.findById(1)
                .orElseThrow();
        assertNotNull(refrigerator);
    }

    @Test
    void testFind2() {
        Refrigerator refrigerator = refrigeratorDao.findFullById(2)
                .orElseThrow();
        assertNotNull(refrigerator);
        assertNotNull(refrigerator.getRefrigeratorCosts());
    }

    @Test
    void testFind3() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findById(-1);
        assertTrue(refrigerator.isEmpty());
    }

    @Test
    void testFind4() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findFullById(-1);
        assertTrue(refrigerator.isEmpty());
    }
}
