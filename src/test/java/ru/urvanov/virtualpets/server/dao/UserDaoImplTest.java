package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import ru.urvanov.virtualpets.server.dao.domain.User;

@Sql({ "/ru/urvanov/virtualpets/server/clean.sql",
        "UserDaoImplTest.sql" })
class UserDaoImplTest extends BaseDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    void findByName() throws Exception {
        User user = userDao.findByLogin("Clarence").orElseThrow();
        assertNotNull(user);
    }
    
    @Test
    void findLastRegisteredUsers() throws Exception {
        List<User> users = userDao.findLastRegisteredUsers(0, 999999);
        assertEquals(users.size(), 1);
    }

}
