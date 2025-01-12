package com.duongvct.service;

import com.duongvct.entity.Author;
import com.duongvct.repository.AuthorRepository;
import com.duongvct.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(int id) {
        return authorRepository.findById(id).orElse(null);
    }


    public void updateAuthor(int id, Author author) {
        Author existingAuthor = authorRepository.findById(id).orElse(null);
        if (existingAuthor != null) {
            existingAuthor.setAuthorName(author.getAuthorName());
            existingAuthor.setBooks(author.getBooks());
            authorRepository.save(existingAuthor);
        }
    }

    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    public void deleteAuthor(int id) {
        authorRepository.deleteById(id);
    }

    public void addBookToAuthor(int authorId, int bookId) {
        Author author = authorRepository.findById(authorId).orElse(null);
        if (author != null) {
            author.getBooks().add(bookRepository.findById(bookId).orElse(null));
            authorRepository.save(author);
        }
    }

}
