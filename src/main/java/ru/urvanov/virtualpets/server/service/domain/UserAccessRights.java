package ru.urvanov.virtualpets.server.service.domain;

public class UserAccessRights {
    private Integer id;
    private String login;
    private String name;
    private boolean enabled;
    private String[] allRoles;
    private String[] roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(String[] allRoles) {
        this.allRoles = allRoles;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
