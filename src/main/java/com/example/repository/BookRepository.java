package com.example.repository;

import com.common.entity.BaseEntity;
import com.common.repository.BaseRepository;
import com.example.entity.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by oguzhanonder - 1.10.2018
 */
@Repository
public interface BookRepository extends BaseRepository<Book> {
    Book findByTitle(String title);
}
