package com.common.security;

import com.common.entity.Role;

import java.util.Collection;

/**
 * Created by oguzhanonder - 18.01.2019
 */
public class CustomUserDetails {

    private String email;
    private String name;
    private String surname;
    private String sessionId;
    private Collection<Role> roleList;
    private boolean isActive;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Collection<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(Collection<Role> roleList) {
        this.roleList = roleList;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
