package com.common.security;

import com.common.entity.Role;
import com.common.entity.User;

import java.util.Collection;

/**
 * Created by oguzhanonder - 18.01.2019
 */
public class CustomUserDetails{

    private String email;
    private String id;
    private String name;
    private String surname;
    private String sessionId;
    private String domain;
    private Collection<Role> roleList;
    private boolean isActive;

    public CustomUserDetails(String id,String email, String name, String surname, Collection<Role> roleList, boolean isActive) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.roleList = roleList;
        this.isActive = isActive;
    }

    public CustomUserDetails(String sessionId) {
        this.sessionId = sessionId;
    }

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
