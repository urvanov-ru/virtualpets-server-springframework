package ru.urvanov.virtualpets.server.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

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
        Optional<User> user = userDao.findByLogin("Clarence");
        assertTrue(user.isPresent());
    }
    
    @Test
    void findLastRegisteredUsers() throws Exception {
        List<User> users = userDao.findLastRegisteredUsers(0, 999999);
        assertEquals(1, users.size());
    }

}
