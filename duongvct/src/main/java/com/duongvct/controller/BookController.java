package com.duongvct.controller;

import com.duongvct.entity.Author;
import com.duongvct.entity.Book;
import com.duongvct.entity.Reader;
import com.duongvct.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);

    }
    @GetMapping("/")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }


    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable int id, @RequestBody Book book) {
        bookService.updateBook(id, book);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }

    @PostMapping("/{id}/authors/{authorId}")
    public void addAuthorToBook(@PathVariable int id, @PathVariable int authorId) {
        bookService.addAuthorToBook(id, authorId);
    }





}
