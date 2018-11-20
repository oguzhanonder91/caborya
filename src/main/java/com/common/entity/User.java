package com.common.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;

import org.jboss.aerogear.security.otp.api.Base32;

import java.util.Collection;

/**
 * Created by oguzhanonder - 18.10.2018
 */
@Entity
@Where(clause = "entity_state=1")
public class User extends BaseEntity {

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    @Email
    private String email;

    @Column(length = 60)
    private String password;

    @Column
    private boolean enabled;

    @Column
    private String secret;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private Collection<Role> roles;


    public User() {
        super();
        this.secret = Base32.random();
        this.enabled = false;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}

