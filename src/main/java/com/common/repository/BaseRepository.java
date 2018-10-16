package com.common.repository;

import com.common.entity.BaseEntity;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by oguzhanonder - 3.10.2018
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T,String>{
}
