package ru.urvanov.virtualpets.server.service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.api.domain.LoginArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RefreshUsersOnlineResult;
import ru.urvanov.virtualpets.server.api.domain.UserInfo;
import ru.urvanov.virtualpets.server.api.domain.UserInformation;
import ru.urvanov.virtualpets.server.api.domain.UserInformationArg;
import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
import ru.urvanov.virtualpets.server.service.domain.UserPetDetails;
import ru.urvanov.virtualpets.server.service.domain.UserProfile;
import ru.urvanov.virtualpets.server.service.exception.IncompatibleVersionException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service("userService")
public class UserServiceImpl
        implements UserService, UserApiService, UserDetailsService {

    private static final DateTimeFormatter unidDateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyyMMddHHmmssSSS", Locale.ROOT);

    @Autowired
    private UserDao userDao;

    @Autowired
    private String version;

    @Autowired
    private Clock clock;

    @Override
    @Transactional(rollbackFor = { DaoException.class,
            ServiceException.class })
    public LoginResult login(UserPetDetails userPetDetails, LoginArg loginArg)
            throws ServiceException, DaoException {
        String clientVersion = loginArg.version();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version,
                    clientVersion);
        }
        User user = userDao.findById(userPetDetails.getUserId());

        byte[] b = new byte[256];
        Random r = new Random();
        r.nextBytes(b);
        String uniqueIdentifier = Base64.encodeBase64String(b);
        uniqueIdentifier = uniqueIdentifier + unidDateTimeFormatter
                .format(OffsetDateTime.now(clock));
        user.setUnid(uniqueIdentifier);

        return new LoginResult(true, null, user.getId(),
                uniqueIdentifier);
    }

    @Override
    public RefreshUsersOnlineResult getUsersOnline(
            UserPetDetails userPetDetails) {
        List<User> users = userDao.findOnline();
        List<UserInfo> userInfos = users.stream()
                .map(u -> new UserInfo(u.getId(), u.getName()))
                .collect(Collectors.toList());
        return new RefreshUsersOnlineResult(userInfos);
    }

    @Override
    public UserInformation getUserInformation(
            UserPetDetails userPetDetails,
            UserInformationArg userInformationArg) {
        Integer userId = userInformationArg.getUserId();
        User user = userDao.findById(userId);
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
    @Transactional(rollbackFor = { DaoException.class,
            ServiceException.class })
    public void closeSession(UserPetDetails userPetDetails)
            throws DaoException, ServiceException {
        User user = userDao.findById(userPetDetails.getUserId());
        user.setUnid(null);
    }

    @Override
    public void updateUserInformation(
            UserPetDetails userPetDetails,
            UserInformation userInformation)
            throws ServiceException, DaoException {
        byte[] photo = userInformation.getPhoto();
        if (photo != null) {
            if (photo.length > 100000) {
                throw new ServiceException("Too big file.");
            }
        }
        if (userPetDetails.getUserId().equals(userInformation.getId())) {
            User user = userDao.findById(userPetDetails.getUserId());
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
    public User findByRecoverPasswordKey(String recoverPasswordKey) {
        return userDao.findByRecoverPasswordKey(recoverPasswordKey);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userDao.findByLogin(username);
        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getAuthorities());
    }

}
