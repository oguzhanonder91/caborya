package com.common.service;

import com.common.entity.Role;

/**
 * Created by oguzhanonder - 19.10.2018
 */
public interface RoleService extends BaseService<Role> {

    Role findByName(String name);

    Role findByCode(String code);
}
