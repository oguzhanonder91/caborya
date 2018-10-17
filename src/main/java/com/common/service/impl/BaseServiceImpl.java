package com.common.service.impl;

import com.common.repository.BaseRepository;
import com.common.entity.BaseEntity;
import com.common.service.BaseService;
import com.example.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by oguzhanonder - 3.10.2018
 */
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    @Autowired
    private BaseRepository baseRepository;

    @Override
    public List<T> saveAll(List<T> list) {
        list.stream()
                .peek(s->s.setEntityState(BaseEntity.EntityState.ACTIVE))
                .collect(Collectors.toList());
        return baseRepository.saveAll(list);
    }

    @Override
    public List<T> findAllById(List<String> strings) {
        return baseRepository.findAllById(strings);
    }

    @Override
    public void deleteAll(List<T> list) {
        list.stream()
                .peek(s->s.setEntityState(BaseEntity.EntityState.PASSIVE))
                .collect(Collectors.toList());
        baseRepository.saveAll(list);
    }

    @Override
    public T getOne(String id) {
        return (T) baseRepository.getOne(id);
    }

    @Override
    public Optional<T> findById(String id) {
        return baseRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        Optional<T> t = baseRepository.findById(id);
        t.get().setEntityState(BaseEntity.EntityState.PASSIVE);
        baseRepository.save(t);
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public void flush() {
        baseRepository.flush();
    }

    @Override
    public  List<T> findAll(Example<T> example, Sort sort) {
        return baseRepository.findAll(example, sort);
    }

    @Override
    public T saveAndFlush(T entity) {
        return (T) baseRepository.saveAndFlush(entity);
    }

    @Override
    public List<T> findAll(Example<T> example) {
        return baseRepository.findAll(example);
    }

    @Override
    public void deleteAllInBatch() {
        baseRepository.deleteAllInBatch();
    }

    @Override
    public  List<T>  findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    public T save(T t) {
        t.setEntityState(BaseEntity.EntityState.ACTIVE);
        return (T) baseRepository.save(t);
    }

    @Override
    public void delete(T t) {
        t.setEntityState(BaseEntity.EntityState.PASSIVE);
        baseRepository.save(t);
    }

    @Override
    public long count() {
        return baseRepository.count();
    }

    @Override
    public T update(T t) {
        t.setEntityState(BaseEntity.EntityState.ACTIVE);
        return (T) baseRepository.save(t);
    }
}
