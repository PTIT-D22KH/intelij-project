package com.duongvct.controller;

import com.duongvct.entity.Author;
import com.duongvct.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @GetMapping("/")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }


    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) {
        return authorService.getAuthorById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateAuthor(@PathVariable int id, @RequestBody Author author) {
        authorService.updateAuthor(id, author);
    }

    @PostMapping("/")
    public void addAuthor(@RequestBody Author author) {
        authorService.addAuthor(author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
    }

    @PostMapping("/{id}/books/{bookId}")
    public ResponseEntity<Void> addBookToAuthor(@PathVariable int id, @PathVariable int bookId) {
        authorService.addBookToAuthor(id, bookId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{bookId}")
                .buildAndExpand(bookId)
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
