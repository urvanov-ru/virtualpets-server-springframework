package ru.urvanov.virtualpets.server.service;

import java.util.List;
import java.util.Optional;

import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.domain.UserAccessRights;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;
import ru.urvanov.virtualpets.server.service.exception.UserNotFoundException;

public interface UserService {

    UserProfile getProfile(Integer integer)
            throws UserNotFoundException;

    List<User> findLastRegisteredUsers(int start, int limit);

    Optional<User> findByRecoverPasswordKey(String recoverPasswordKey);
    
    UserAccessRights findUserAccessRights(Integer id)
                    throws UserNotFoundException;

    UserAccessRights saveUserAccessRights(UserAccessRights userAccessRights)
            throws UserNotFoundException;

}
