package com.common.repository;

import com.common.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by oguzhanonder - 3.10.2018
 */
@Repository
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T,String>{
}
