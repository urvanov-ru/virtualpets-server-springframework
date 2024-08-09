package ru.urvanov.virtualpets.server.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Реализация интерфейса
 * {@link org.springframework.security.core.userdetails.UserDetails}.
 * Добавляет два поля:
 * <ul>
 *   <li>
 *   userId - первичный ключ пользователя,
 *   </li>
 *   <li>
 *   name - полное отображаемое имя пользователя.
 *   </li>
 * </ul>
 */
public class UserDetailsImpl extends User {

    private static final long serialVersionUID = -3285304553448604871L;

    /**
     * Первичный ключ пользователя.
     */
    private Integer userId;
    
    /**
     * Полное отображаемое имя пользователя.
     */
    private String name;

    /**
     * Инициализирует экземпляр UserDetailsImpl.
     * @param userId Первичный ключ пользователя.
     * @param username Логин.
     * @param name Полное отображаемое имя пользователя.
     * @param password Пароль.
     * @param enabled Учётная запись включена.
     * @param authorities Список ролей.
     */
    public UserDetailsImpl(Integer userId, String username, String name,
            String password,
            boolean enabled,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
        this.userId = userId;
        this.name = name;
    }

    /**
     * @return Первичный ключ пользователя
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @return Полное отображаемое имя пользователя.
     */
    public String getName() {
        return name;
    }
}
