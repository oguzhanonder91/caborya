package com.example.controller;

import com.common.exception.BaseNotFoundException;
import com.example.entity.Book;
import com.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by oguzhanonder - 1.10.2018
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/title/{bookTitle}")
    public Book findByTitle(@PathVariable String bookTitle) {
        return bookService.findByTitle(bookTitle);
    }

    @GetMapping("/{id}")
    public Book findOne(@PathVariable String id) {
        Optional<Book> book = bookService.findById(id);
        if (!book.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Kitap Bulunamadı-");
        return book.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book book) {
        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        Optional<Book> book = bookService.findById(id);
        if (!book.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Kitap Bulunamadı-");
        bookService.delete(book.get());
    }

    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable String id) {
        Optional<Book> oldBook = bookService.findById(id);
        if (!oldBook.isPresent())
            throw new BaseNotFoundException(id + " - Bu id ye ait Kitap Bulunamadı-");
        return bookService.update(book);
    }

}
