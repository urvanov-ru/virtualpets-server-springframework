/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.RefrigeratorDao;
import ru.urvanov.virtualpets.server.dao.domain.Refrigerator;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class RefrigeratorDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private RefrigeratorDao refrigeratorDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind1() {
        Refrigerator refrigerator = refrigeratorDao.findById(1);
        assertNotNull(refrigerator);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind2() {
        Refrigerator refrigerator = refrigeratorDao.findFullById(2);
        assertNotNull(refrigerator);
        assertNotNull(refrigerator.getRefrigeratorCost());
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind3() {
        Refrigerator refrigerator = refrigeratorDao.findById(-1);
        assertNull(refrigerator);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/RefrigeratorServiceImplTest.xls")
    @Test
    public void testFind4() {
        Refrigerator refrigerator = refrigeratorDao.findFullById(-1);
        assertNull(refrigerator);
    }
}
