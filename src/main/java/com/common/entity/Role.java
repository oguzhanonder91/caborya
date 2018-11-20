package com.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by oguzhanonder - 18.10.2018
 */
@Entity
@Where(clause = "entity_state=1")
public class Role extends BaseEntity {

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<User> users;

    @Column
    private String name;

    @Column
    private String code;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
