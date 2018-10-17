package com.example.service.impl;

import com.common.service.impl.BaseServiceImpl;
import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Created by oguzhanonder - 3.10.2018
 */

@Service
public class BookServiceImpl extends BaseServiceImpl<Book> implements BookService {

    @Autowired BookRepository bookRepository;

    @Override
    public Book findByTitle(String bookTitle) {
        return bookRepository.findByTitle(bookTitle);
    }
}
