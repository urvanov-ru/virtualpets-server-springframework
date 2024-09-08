package ru.urvanov.virtualpets.server.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.Bookcase;

@Sql("/ru/urvanov/virtualpets/server/clean.sql")
class BookcaseDaoImplTest extends BaseDaoImplTest {
    
    @Autowired
    BookcaseDao bookcaseDao;

    
    @Test
    void testFind1() {
        Bookcase bookcase = bookcaseDao.findById(1).orElseThrow();
        assertNotNull(bookcase);
    }
    
    
    @Test
    void testFind2() {
        Bookcase bookcase = bookcaseDao.findFullById(1).orElseThrow();
        assertNotNull(bookcase);
        assertNotNull(bookcase.getBookcaseCosts());
    }
    
    @Test
    void testFind3() {
        Optional<Bookcase> bookcase = bookcaseDao.findById(-1);
        assertTrue(bookcase.isEmpty());
    }
    
    @Test
    void testFind4() {
        assertFalse(bookcaseDao.findFullById(-1).isPresent());
    }
}
