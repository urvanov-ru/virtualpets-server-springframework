package ru.urvanov.virtualpets.server.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsImpl extends User {

    private Integer userId;
    
    private String name;

    public UserDetailsImpl(Integer userId, String username, String name,
            String password,
            boolean enabled,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.userId = userId;
        this.name = name;
    }

    private static final long serialVersionUID = -3285304553448604871L;

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
