/**
 * 
 */
package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.test.annotation.DataSets;

/**
 * @author fedya
 * 
 */
public class UserDaoImplTest extends AbstractDaoImplTest {

    @Autowired
    private UserDao userDao;

    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/UserServiceImplTest.xls")
    @Test
    public void findByName() throws Exception {
        User user = userDao.findByLogin("Clarence");
        assertNotNull(user);
    }
    
    @DataSets(setUpDataSet = "/ru/urvanov/virtualpets/server/service/UserServiceImplTest.xls")
    @Test
    public void findLastRegisteredUsers() throws Exception {
        List<User> users = userDao.findLastRegisteredUsers(0, 999999);
        assertEquals(users.size(), 1);
    }

}
