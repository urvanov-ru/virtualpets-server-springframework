package ru.urvanov.virtualpets.server.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.urvanov.virtualpets.server.controller.game.domain.LoginArg;
import ru.urvanov.virtualpets.server.controller.game.domain.LoginResult;
import ru.urvanov.virtualpets.server.controller.game.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.controller.game.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.controller.game.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.Role;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.exception.IncompatibleVersionException;
import ru.urvanov.virtualpets.server.service.exception.NameIsBusyException;
import ru.urvanov.virtualpets.server.service.exception.SendMailException;
import ru.urvanov.virtualpets.server.service.exception.ServiceException;

@Service
public class PublicServiceImpl implements PublicApiService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    @Autowired
    private String version;

    @Value("${virtualpets-server-springframework.server.url}")
    private String serverUrl;
    
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    
    @Autowired
    private Clock clock;

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void register(RegisterArgument registerArgument)
            throws ServiceException {
        String clientVersion = registerArgument.version();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version,
                    clientVersion);
        }
        Optional<User> existUser = userDao.findByLogin(
                registerArgument.login());
        if (existUser.isPresent()) {
            throw new NameIsBusyException();
        }
        User user = new User();
        user.setLogin(registerArgument.login());
        user.setName(registerArgument.name());
        user.setPassword(bcryptEncoder.encode(registerArgument.password()));
        user.setEmail(registerArgument.email());
        user.setRegistrationDate(OffsetDateTime.now(clock));
        user.setRoles(Role.USER.name());
        user.setEnabled(true);
        userDao.save(user);
    }
    
    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public LoginResult login(LoginArg loginArg)
            throws ServiceException {
        String clientVersion = loginArg.version();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version,
                    clientVersion);
        }
        User user = userDao.findByLogin(loginArg.login()).orElseThrow();
        return new LoginResult(true, null, user.getId(),
                user.getLogin(), user.getName());
    }


    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void recoverPassword(
            RecoverPasswordArg recoverPasswordArgument)
            throws ServiceException {
        String clientVersion = recoverPasswordArgument.version();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }

        String email = recoverPasswordArgument.email();
        String login = recoverPasswordArgument.login();

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(ex);
        }
        byte[] hash = digest.digest();

        // converting byte array to Hexadecimal String
        StringBuilder sb = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        String key = sb.toString();
        OffsetDateTime recoverPasswordValid = OffsetDateTime.now(clock);
        recoverPasswordValid = recoverPasswordValid.minusMonths(1);
        User user = userDao.findByLoginAndEmail(login, email)
                .orElseThrow();
        user.setRecoverPasswordKey(key);
        user.setRecoverPasswordValid(recoverPasswordValid);
        userDao.save(user);

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText("Dear " + user.getName()
                + ", follow this link to recover password " + serverUrl
                + "/site/recoverPassword?recoverPasswordKey=" + key + " ");
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            throw new SendMailException("Send mail exception.", ex);
        }

    }

    @Override
    public ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException {
        try {
            
            Properties properties = System.getProperties();
            Map<String, String> infoMap = new HashMap<String, String>();
            for (Entry<Object, Object> entry : properties.entrySet()) {
                infoMap.put(String.valueOf(entry.getKey()),
                        String.valueOf(entry.getValue()));
            }
            ServerTechnicalInfo info = new ServerTechnicalInfo(infoMap);
            return info;
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

}
