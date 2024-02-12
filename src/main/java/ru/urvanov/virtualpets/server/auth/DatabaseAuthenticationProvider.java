package ru.urvanov.virtualpets.server.auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;
import ru.urvanov.virtualpets.server.service.UserService;

/**
 * @author fedya
 * 
 */
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        User user;
        try {
            String password = authentication.getCredentials().toString();
            MessageDigest md5;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new AuthenticationException(
                        "MD5 encrypting is not available.", e) {

                    /**
                      * 
                      */
                    private static final long serialVersionUID = 4256048706139297120L;
                };
            }
            String hexPasswordMd5;
            try {
                hexPasswordMd5 = (new HexBinaryAdapter()).marshal(md5
                        .digest(password.getBytes("UTF-8")));
            } catch (UnsupportedEncodingException e) {
                throw new AuthenticationException("UnsupportedEncoding", e){

                    /**
                     * 
                     */
                    private static final long serialVersionUID = -6946605296392709613L;};
            }
            user = userService.findByLoginAndPassword(authentication.getName(),
                    hexPasswordMd5);
        } catch (jakarta.persistence.NoResultException ex) {
            throw new BadCredentialsException(authentication.getName());
        }
        Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();
        granted.add(new SimpleGrantedAuthority("ROLE_USER"));
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user, null, granted);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
