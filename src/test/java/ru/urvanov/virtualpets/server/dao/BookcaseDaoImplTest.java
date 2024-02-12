/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 *
 */
public class BookcaseDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    BookcaseDao bookcaseDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind1() {
        Bookcase bookcase = bookcaseDao.findById(1);
        assertNotNull(bookcase);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind2() {
        Bookcase bookcase = bookcaseDao.findFullById(1);
        assertNotNull(bookcase);
        assertNotNull(bookcase.getBookcaseCost());
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind3() {
        Bookcase bookcase = bookcaseDao.findById(-1);
        assertNull(bookcase);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind4() {
        Bookcase bookcase = bookcaseDao.findFullById(-1);
        assertNull(bookcase);
    }
}
