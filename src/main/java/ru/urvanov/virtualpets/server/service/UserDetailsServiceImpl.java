package ru.urvanov.virtualpets.server.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.urvanov.virtualpets.server.auth.UserDetailsImpl;
import ru.urvanov.virtualpets.server.dao.UserDao;
import ru.urvanov.virtualpets.server.dao.domain.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user = userDao.findByLogin(username);
        return user.map(u -> new UserDetailsImpl(
                u.getId(),
                u.getLogin(),
                u.getName(),
                u.getPassword(),
                u.isEnabled(),
                this.getAuthorities(u)))
                .orElseThrow(() ->
                        new UsernameNotFoundException(username));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            User user) {
        Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();
        StringTokenizer t = new StringTokenizer(user.getRoles(), ",");
        t.asIterator().forEachRemaining((role) ->
                granted.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return granted;
    }

}
