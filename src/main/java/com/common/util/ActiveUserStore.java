package com.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oguzhanonder - 29.10.2018
 */
public class ActiveUserStore {

    public List<String> users;

    public ActiveUserStore() {
        users = new ArrayList<String>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
