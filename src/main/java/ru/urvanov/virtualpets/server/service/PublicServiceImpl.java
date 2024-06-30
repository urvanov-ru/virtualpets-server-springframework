package ru.urvanov.virtualpets.server.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import ru.urvanov.virtualpets.server.api.domain.GetServersArg;
import ru.urvanov.virtualpets.server.api.domain.LoginResult;
import ru.urvanov.virtualpets.server.api.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.server.api.domain.RecoverSessionArg;
import ru.urvanov.virtualpets.server.api.domain.RegisterArgument;
import ru.urvanov.virtualpets.server.api.domain.ServerInfo;
import ru.urvanov.virtualpets.server.api.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.dao.exception.DaoException;
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

    @Value("${virtualpets-server-springframework.servers.url}")
    private String[] serversUrl;
    
    @Value("${virtualpets-server-springframework.servers.name}")
    private String[] serversName;
    
    @Value("${virtualpets-server-springframework.servers.locale}")
    private String[] serversLocale;

    private ServerInfo[] servers;
    
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    
    @Autowired
    private Clock clock;

    @Override
    public ServerInfo[] getServers(GetServersArg arg) throws ServiceException,
            DaoException {
        String clientVersion = arg.version();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }
        return servers;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void register(RegisterArgument arg) throws ServiceException, DaoException {
        try {
            String clientVersion = arg.version();
            if (!version.equals(clientVersion)) {
                throw new IncompatibleVersionException("", version,
                        clientVersion);
            }
            User user = userDao.findByLogin(arg.login());
            if (user != null) {
                throw new NameIsBusyException();
            }
            if (user == null) {
                throw new jakarta.persistence.NoResultException();
            }
        } catch (jakarta.persistence.NoResultException noResultException) {
            User user = new User();
            user.setLogin(arg.login());
            user.setName(arg.login());
            user.setPassword(bcryptEncoder.encode(arg.password()));
            user.setEmail(arg.email());
            user.setRegistrationDate(OffsetDateTime.now(clock));
            user.setRole(ru.urvanov.virtualpets.server.dao.domain.Role.USER);
            userDao.save(user);
        }
    }


    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void recoverPassword(RecoverPasswordArg argument)
            throws ServiceException, DaoException {
        String clientVersion = argument.version();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }

        String email = argument.email();
        String login = argument.login();

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
        User user = userDao.findByLoginAndEmail(login, email);
        user.setRecoverPasswordKey(key);
        user.setRecoverPasswordValid(recoverPasswordValid);
        userDao.save(user);

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText("Dear " + user.getName()
                + ", follow this link to recover password " + argument.host()
                + "/site/recoverPassword?recoverPasswordKey=" + key + " ");
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            throw new SendMailException("Send mail exception.", ex);
        }

    }

    @Override
    @Transactional(rollbackFor = ServiceException.class, readOnly = true)
    public LoginResult recoverSession(RecoverSessionArg arg)
            throws ServiceException, DaoException {
        String clientVersion = arg.version();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String unid = arg.unid();
        User user = userDao.findByUnid(unid);
        if (user != null) {

            Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();
            granted.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user, null, granted);
            securityContext.setAuthentication(token);
        }
        Authentication auth = securityContext.getAuthentication();

        LoginResult loginResult;
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            loginResult = new LoginResult(true, null, user.getId(), user.getUnid());
        } else {
            throw new ServiceException("Failed to recover session");
        }
        return loginResult;
    }

    @Override
    public ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException, DaoException {
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
    
    @PostConstruct
    public void init() {

        if ((serversUrl.length != serversName.length)
                || (serversName.length != serversLocale.length)) {
            throw new IllegalStateException("application.servers.url, application.servers.name, application.servers.locale should have the same count of elements. Elements are splitted by '|'.");
        }
        servers = new ServerInfo[serversUrl.length];
        for (int n = 0; n < serversUrl.length; n++) {
            servers[n] = new ServerInfo(
                    serversUrl[n],
                    serversName[n],
                    serversLocale[n]);
        }
    }

}
