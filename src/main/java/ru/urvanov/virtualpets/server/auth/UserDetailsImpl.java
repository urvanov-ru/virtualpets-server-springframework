package ru.urvanov.virtualpets.server.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsImpl extends User {

    private Integer userId;
    
    public UserDetailsImpl(Integer userId, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    private static final long serialVersionUID = -3285304553448604871L;

    public Integer getUserId() {
        return userId;
    }

}
