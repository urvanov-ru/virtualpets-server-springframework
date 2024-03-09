package ru.urvanov.virtualpets.server.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author fedya
 * 
 */
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseAuthenticationProvider.class);
    
    private UserDetailsService userDetailsService;
    
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        logger.info("authenticate started.");
        String password = authentication.getCredentials().toString();
        String userName = authentication.getName();
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(userName);
        if (userDetails == null) {
            logger.error("User not found. UserName=" + userName);
            throw new BadCredentialsException(userName);
        } else if (!userDetails.isEnabled()) {
            logger.error("Not enabled.");
            throw new DisabledException(userName);
        } else {
            if (bcryptEncoder.matches(password, userDetails.getPassword())) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                logger.info("authenticate finished.");
                return token;
            } else {
                logger.error("Password does not match. UserName=" + userName);
                throw new BadCredentialsException(userName);
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public BCryptPasswordEncoder getBcryptEncoder() {
        return bcryptEncoder;
    }

    public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
        this.bcryptEncoder = bcryptEncoder;
    }


}
