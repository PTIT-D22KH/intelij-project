package com.duongvct.controller;

import com.duongvct.entity.Reader;
import com.duongvct.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/readers")
public class ReaderController {
    @Autowired
    private ReaderService readerService;


    @GetMapping("/")
    public List<Reader> getAllReaders() {
        return readerService.getAllReaders();
    }

    @GetMapping("/{id}")
    public Reader getReaderById(@PathVariable int id) {
        return readerService.getReaderById(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateReader(@PathVariable int id, @RequestBody Reader reader) {
        readerService.updateReader(id, reader);
    }

    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addReader(@RequestBody Reader reader) {
        readerService.addReader(reader);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReader(@PathVariable int id) {
        readerService.deleteReader(id);
    }

    @PostMapping("/{id}/books/{bookId}")
    public ResponseEntity<Void> addBookToReader(@PathVariable int id, @PathVariable int bookId) {
        readerService.addBookToReader(id, bookId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{bookId}")
                .buildAndExpand(bookId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
