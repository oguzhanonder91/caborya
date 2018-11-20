package com.common.repository;

import com.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by oguzhanonder - 19.10.2018
 */
@Repository
public interface RoleRepository extends BaseRepository<Role> {

    Role findByName(String name);

    Role findByCode(String code);
}
