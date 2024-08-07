package ru.urvanov.virtualpets.server.dao;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.User;

public interface UserDao {

    void save(User user);

    Optional<User> findByLogin(String login);

    List<User> list();

    Optional<User> findById(Integer id);

    List<User> findOnline();

    Optional<User> findByLoginAndEmail(String name, String email);

    Optional<User> findByRecoverPasswordKey(String recoverKey);

    List<User> findLastRegisteredUsers(int start, int limit);

    User getReference(Integer id);
}
