/**
 * 
 */
package ru.urvanov.virtualpets.server.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.stereotype.Service;

import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.shared.domain.GetServersArg;
import ru.urvanov.virtualpets.shared.domain.LoginResult;
import ru.urvanov.virtualpets.shared.domain.RecoverPasswordArg;
import ru.urvanov.virtualpets.shared.domain.RecoverPasswordResult;
import ru.urvanov.virtualpets.shared.domain.RecoverSessionArg;
import ru.urvanov.virtualpets.shared.domain.RegisterArgument;
import ru.urvanov.virtualpets.shared.domain.ServerInfo;
import ru.urvanov.virtualpets.shared.domain.ServerTechnicalInfo;
import ru.urvanov.virtualpets.shared.exception.DaoException;
import ru.urvanov.virtualpets.shared.exception.IncompatibleVersionException;
import ru.urvanov.virtualpets.shared.exception.NameIsBusyException;
import ru.urvanov.virtualpets.shared.exception.SendMailException;
import ru.urvanov.virtualpets.shared.exception.ServiceException;
import ru.urvanov.virtualpets.shared.service.PublicService;

/**
 * @author fedya
 * 
 */
@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    @Autowired
    private String version;

    @Value("${application.url}")
    private String applicationUrl;

    @Override
    public ServerInfo[] getServers(GetServersArg arg) throws ServiceException,
            DaoException {
        String clientVersion = arg.getVersion();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }
        ServerInfo[] serverInfos = new ServerInfo[1];
        serverInfos[0] = new ServerInfo();
        serverInfos[0].address = "http://localhost:8081/virtualpets-server/site";
        serverInfos[0].locale = "ru";
        serverInfos[0].name = "Русскоязычный основной";
        return serverInfos;
    }

    @Override
    public void register(RegisterArgument arg) throws ServiceException {
        try {
            String clientVersion = arg.getVersion();
            if (!version.equals(clientVersion)) {
                throw new IncompatibleVersionException("", version,
                        clientVersion);
            }
            User user = userDao.findByLogin(arg.getLogin());
            if (user != null) {
                throw new NameIsBusyException();
            }
            if (user == null) {
                throw new jakarta.persistence.NoResultException();
            }
        } catch (jakarta.persistence.NoResultException noResultException) {
            User user = new User();
            user.setLogin(arg.getLogin());
            user.setName(arg.getLogin());
            MessageDigest md5;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new ServiceException("MD5 is not available.", e);
            }
            String hexPasswordMd5 = null;
            try {
                hexPasswordMd5 = (new HexBinaryAdapter()).marshal(md5
                        .digest(arg.getPassword().getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                throw new ServiceException(e);
            }
            user.setPassword(hexPasswordMd5);
            user.setEmail(arg.getEmail());
            user.setRegistrationDate(new Date());
            user.setRole(ru.urvanov.virtualpets.server.dao.domain.Role.USER);
            userDao.save(user);
        }
    }


    @Override
    public RecoverPasswordResult recoverPassword(RecoverPasswordArg argument)
            throws ServiceException {
        String clientVersion = argument.getVersion();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }

        String email = argument.getEmail();
        String login = argument.getLogin();

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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        User user = userDao.findByLoginAndEmail(login, email);
        user.setRecoverPasswordKey(key);
        user.setRecoverPasswordValid(calendar.getTime());
        userDao.save(user);

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(email);
        msg.setText("Dear " + user.getName()
                + ", follow this link to recover password " + applicationUrl
                + "/site/recoverPassword?recoverPasswordKey=" + key + " ");
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            throw new SendMailException("Send mail exception.", ex);
        }

        RecoverPasswordResult result = new RecoverPasswordResult();

        result.setSuccess(true);
        return result;
    }

    /**
     * @return the mailSender
     */
    public MailSender getMailSender() {
        return mailSender;
    }

    /**
     * @param mailSender
     *            the mailSender to set
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * @return the templateMessage
     */
    public SimpleMailMessage getTemplateMessage() {
        return templateMessage;
    }

    /**
     * @param templateMessage
     *            the templateMessage to set
     */
    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    @Override
    public LoginResult recoverSession(RecoverSessionArg arg)
            throws ServiceException, DaoException {
        String clientVersion = arg.getVersion();
        if (!version.equals(clientVersion)) {
            throw new IncompatibleVersionException("", version, clientVersion);
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String unid = arg.getUnid();
        User user = userDao.findByUnid(unid);
        if (user != null) {

            Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();
            granted.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    user, null, granted);
            securityContext.setAuthentication(token);
        }
        Authentication auth = securityContext.getAuthentication();

        LoginResult loginResult = new LoginResult();
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            loginResult.setSuccess(true);
            loginResult.setUnid(user.getUnid());
            loginResult.setUserId(user.getId());
        } else {
            loginResult.setSuccess(false);
        }
        return loginResult;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the applicationUrl
     */
    public String getApplicationUrl() {
        return applicationUrl;
    }

    /**
     * @param applicationUrl
     *            the applicationUrl to set
     */
    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    @Override
    public ServerTechnicalInfo getServerTechnicalInfo()
            throws ServiceException, DaoException {
        try {
            ServerTechnicalInfo info = new ServerTechnicalInfo();
            Properties properties = System.getProperties();
            Map<String, String> infoMap = new HashMap<String, String>();
            for (Entry<Object, Object> entry : properties.entrySet()) {
                infoMap.put(String.valueOf(entry.getKey()),
                        String.valueOf(entry.getValue()));
            }
            info.setInfo(infoMap);
            return info;
        } catch (Exception ex) {
            throw new ServiceException(ex);
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
