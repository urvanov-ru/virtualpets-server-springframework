package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

public class BookcaseDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    BookcaseDao bookcaseDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind1() {
        Bookcase bookcase = bookcaseDao.findById(1).orElseThrow();
        assertNotNull(bookcase);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind2() {
        Bookcase bookcase = bookcaseDao.findFullById(1).orElseThrow();
        assertNotNull(bookcase);
        assertNotNull(bookcase.getBookcaseCost());
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind3() {
        Optional<Bookcase> bookcase = bookcaseDao.findById(-1);
        assertTrue(bookcase.isEmpty());
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/BookcaseServiceImplTest.xls")
    @Test
    void testFind4() {
        assertFalse(bookcaseDao.findFullById(-1).isPresent());
    }
}
