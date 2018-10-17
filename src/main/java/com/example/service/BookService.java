package com.example.service;

import com.common.service.BaseService;
import com.example.entity.Book;
import org.springframework.stereotype.Service;

/**
 * Created by oguzhanonder - 3.10.2018
 */

public interface BookService extends BaseService<Book>{
    Book findByTitle(String bookTitle);
}
