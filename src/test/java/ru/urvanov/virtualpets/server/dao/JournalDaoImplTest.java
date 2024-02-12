/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.JournalEntryDao;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntry;
import ru.urvanov.virtualpets.server.dao.domain.JournalEntryType;

/**
 * @author fedya
 *
 */
public class JournalDaoImplTest extends AbstractDaoImplTest {
    
    @Autowired
    private JournalEntryDao journalEntryDao;


    @Test
    public void testFind1() {
        JournalEntry journalEntry = journalEntryDao.findById(1);
        assertNotNull(journalEntry);
    }
    
    
    @Test
    public void testFind2() {
        JournalEntry journalEntry = journalEntryDao.findByCode(JournalEntryType.WELCOME);
        assertNotNull(journalEntry);
    }
    
    
    @Test
    public void testFind3() {
        JournalEntry journalEntry = journalEntryDao.getReference(1);
        assertNotNull(journalEntry);;
    }
    

}
