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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.ServletRequestAttributes;

import ru.urvanov.virtualpets.server.api.domain.LoginArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RefreshUsersOnlineResult;
import ru.urvanov.virtualpets.server.api.domain.UserInfo;
import ru.urvanov.virtualpets.server.api.domain.UserInformation;
import ru.urvanov.virtualpets.server.api.domain.UserInformationArg;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
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
    public LoginResult login(LoginArg arg)
            throws ServiceException, DaoException {
        String clientVersion = arg.version();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version,
                    clientVersion);
        }
        org.springframework.web.context.request.ServletRequestAttributes sra = (org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder
                .getRequestAttributes();
        sra.setAttribute("my1", "var1",
                ServletRequestAttributes.SCOPE_SESSION);
        SecurityContext securityContext = SecurityContextHolder
                .getContext();
        Authentication authentication = securityContext
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        user = userDao.findById(user.getId());

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
    public RefreshUsersOnlineResult getUsersOnline() {
        List<User> users = userDao.findOnline();
        List<UserInfo> userInfos = users.stream()
                .map(u -> new UserInfo(u.getId(), u.getName()))
                .collect(Collectors.toList());
        return new RefreshUsersOnlineResult(userInfos);
    }

    @Override
    public UserInformation getUserInformation(
            UserInformationArg argument) {
        Integer userId = argument.getUserId();
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
    public void closeSession() throws DaoException, ServiceException {
        SecurityContext securityContext = SecurityContextHolder
                .getContext();
        Authentication authentication = securityContext
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        user = userDao.findById(user.getId());
        user.setUnid(null);
    }

    @Override
    public void updateUserInformation(UserInformation arg)
            throws ServiceException, DaoException {
        byte[] photo = arg.getPhoto();
        if (photo != null) {
            if (photo.length > 100000) {
                throw new ServiceException("Too big file.");
            }
        }
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        User user = (User) auth.getPrincipal();
        Integer id = user.getId();
        if (id.equals(arg.getId())) {
            user = userDao.findById(id);
            user.setName(arg.getName());
            user.setSex(arg.getSex());
            user.setBirthdate(arg.getBirthdate());
            user.setCountry(arg.getCountry());
            user.setCity(arg.getCity());
            user.setComment(arg.getComment());
            // user.setPhoto(arg.getPhoto());
            userDao.save(user);
        } else {
            throw new ServiceException(
                    "Incorrect user id. You can not save this user information.");
        }
    }

    @Override
    public UserProfile getProfile() {
        SecurityContext securityContext = SecurityContextHolder
                .getContext();
        Authentication authentication = securityContext
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        UserProfile userProfile = new UserProfile();
        userProfile.setBirthdate(user.getBirthdate());
        userProfile.setName(user.getName());
        userProfile.setEmail(user.getEmail());
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
        return userDao.findByLogin(username);
    }

}
