package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

public class RefrigeratorDaoImplTest extends AbstractDaoImplTest {

    @Autowired
    private RefrigeratorDao refrigeratorDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind1() {
        Refrigerator refrigerator = refrigeratorDao.findById(1)
                .orElseThrow();
        assertNotNull(refrigerator);
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind2() {
        Refrigerator refrigerator = refrigeratorDao.findFullById(2)
                .orElseThrow();
        assertNotNull(refrigerator);
        assertNotNull(refrigerator.getRefrigeratorCost());
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind3() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findById(-1);
        assertTrue(refrigerator.isEmpty());
    }

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind4() {
        Optional<Refrigerator> refrigerator = refrigeratorDao.findFullById(-1);
        assertTrue(refrigerator.isEmpty());
    }
}
