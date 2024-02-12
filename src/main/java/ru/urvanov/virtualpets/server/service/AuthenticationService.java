/**
 * 
 */
package ru.urvanov.virtualpets.server.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fedya
 *
 */
@Service("authenticationService")
public class AuthenticationService {
    public String getAuthenticatedUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        return principal.toString();
    }
}
