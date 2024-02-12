package ru.urvanov.virtualpets.server.service;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;

public interface UserService {

    UserProfile getProfile();

    List<User> findLastRegisteredUsers(int start, int limit);

    User findByRecoverPasswordKey(String recoverPasswordKey);

    User findByLoginAndPassword(String name, String hexPasswordMd5);

}
