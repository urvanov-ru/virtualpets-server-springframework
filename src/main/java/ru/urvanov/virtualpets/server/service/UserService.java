package ru.urvanov.virtualpets.server.service;

import java.util.List;

import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;

public interface UserService {

    UserProfile getProfile(UserPetDetails userPetDetails);

    List<User> findLastRegisteredUsers(int start, int limit);

    User findByRecoverPasswordKey(String recoverPasswordKey);

}
