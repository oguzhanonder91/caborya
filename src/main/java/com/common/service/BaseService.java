package com.common.service;

import com.common.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by oguzhanonder - 3.10.2018
 */
public interface BaseService<T extends BaseEntity> {

    List<T> findAll();

    List<T> findAll(Sort sort);

    void flush();

    T saveAndFlush(T entity);

    List<T> findAll(Example<T> example, Sort sort);

    List<T> findAll(Example<T> example);

    T getOne(String id);

    void deleteAllInBatch();

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(String id);

    T save(T t);

    T update(T t);

    void deleteById(String id);

    void delete(T t);

    long count();

    boolean existsById(String id);

    List<T> saveAll(List<T> list);

    List<T> findAllById(List<String> strings);

    void deleteAll(List<T> list);


}
