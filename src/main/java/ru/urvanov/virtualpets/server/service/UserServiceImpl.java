package ru.urvanov.virtualpets.server.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.controller.api.domain.RefreshUsersOnlineResult;
import ru.urvanov.virtualpets.server.controller.api.domain.UserInfo;
import ru.urvanov.virtualpets.server.controller.api.domain.UserInformation;
import ru.urvanov.virtualpets.server.controller.api.domain.UserInformationArg;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Role;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.domain.UserAccessRights;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;
import ru.urvanov.virtualpets.server.service.exception.UserNotFoundException;

@Service("userService")
public class UserServiceImpl
        implements UserGameService, UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @PreAuthorize("hasRole('USER')")
    public RefreshUsersOnlineResult getUsersOnline(
            UserPetDetails userPetDetails) 
            throws ServiceException {
        List<User> users = userDao.findOnline();
        List<UserInfo> userInfos = users.stream()
                .map(u -> new UserInfo(u.getId(), u.getName()))
                .collect(Collectors.toList());
        return new RefreshUsersOnlineResult(userInfos);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public UserInformation getUserInformation(
            UserPetDetails userPetDetails,
            UserInformationArg userInformationArg)
            throws ServiceException{
        Integer userId = userInformationArg.getUserId();
        User user = userDao.findById(userId).orElseThrow();
        UserInformation result = new UserInformation();
        result.setId(userId);
        result.setName(user.getName());
        result.setBirthdate(user.getBirthdate());
        result.setCity(user.getCity());
        result.setCountry(user.getCountry());
        result.setComment(user.getComment());
        // result.setPhoto(user.getPhoto());
        result.setSex(user.getSex());
        return result;
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public void updateUserInformation(
            UserPetDetails userPetDetails,
            UserInformation userInformation)
            throws ServiceException {
        byte[] photo = userInformation.getPhoto();
        if (photo != null) {
            if (photo.length > 100000) {
                throw new ServiceException("Too big file.");
            }
        }
        if (userPetDetails.getUserId().equals(userInformation.getId())) {
            User user = userDao.findById(userPetDetails.getUserId())
                    .orElseThrow();
            user.setName(userInformation.getName());
            user.setSex(userInformation.getSex());
            user.setBirthdate(userInformation.getBirthdate());
            user.setCountry(userInformation.getCountry());
            user.setCity(userInformation.getCity());
            user.setComment(userInformation.getComment());
            // user.setPhoto(userInformation.getPhoto());
            userDao.save(user);
        } else {
            throw new ServiceException(
                    "Incorrect user id. You can not save this user information.");
        }
    }

    @Override
    public UserProfile getProfile(UserPetDetails userPetDetails) {
        UserProfile userProfile = new UserProfile();
        userProfile.setBirthdate(userPetDetails.getUserBirthdate());
        userProfile.setName(userPetDetails.getUserName());
        userProfile.setEmail(userPetDetails.getUserEmail());
        return userProfile;
    }

    @Override
    public List<User> findLastRegisteredUsers(int start, int limit) {
        return userDao.findLastRegisteredUsers(start, limit);
    }

    @Override
    public Optional<User> findByRecoverPasswordKey(String recoverPasswordKey) {
        return userDao.findByRecoverPasswordKey(recoverPasswordKey);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserAccessRights findUserAccessRights(Integer id)
            throws UserNotFoundException {
        return userDao.findById(id).map(u -> {
            UserAccessRights userAccessRights = new UserAccessRights();
            userAccessRights.setId(u.getId());
            userAccessRights.setName(u.getName());
            userAccessRights.setLogin(u.getLogin());
            userAccessRights.setEnabled(u.isEnabled());
            userAccessRights.setAllRoles(Arrays.stream(Role.values())
                    .map(Role::name).toArray(String[]::new));
            userAccessRights.setRoles(u.getRoles().split(","));
            return userAccessRights;
        }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') && (#userAccessRights.id ne principal.userId)")
    @Transactional(rollbackFor = ServiceException.class)
    public UserAccessRights saveUserAccessRights(@P("userAccessRights") UserAccessRights userAccessRights)
            throws UserNotFoundException {
        User user = userDao.findById(userAccessRights.getId())
                .orElseThrow(() -> new UserNotFoundException(
                        userAccessRights.getId()));
        
        user.setRoles(String.join(",", userAccessRights.getRoles()));
        user.setEnabled(userAccessRights.isEnabled());
        return this.findUserAccessRights(userAccessRights.getId());
    }
}
