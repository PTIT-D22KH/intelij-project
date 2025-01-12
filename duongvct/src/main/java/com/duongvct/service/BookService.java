package com.duongvct.service;

import com.duongvct.entity.Author;
import com.duongvct.entity.Book;
import com.duongvct.repository.AuthorRepository;
import com.duongvct.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


//    @Transactional
//    public void addBookToReader(int bookId, int readerId) {
//        Book book = bookRepository.findById(bookId).orElse(null);
//        if (book != null) {
//            book.getReaders().add(readerId);
//            bookRepository.save(book);
//        }
//    }

    @Transactional
    public void addAuthorToBook(int bookId, int authorId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            Author author = authorRepository.findById(authorId).orElse(null);
            book.setAuthor(author);
            bookRepository.save(book);
        }
    }
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }


    public void updateBook(int id, Book book) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null) {
            existingBook.setBookName(book.getBookName());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setReaders(book.getReaders());
            existingBook.setBookPrice(book.getBookPrice());
            bookRepository.save(existingBook);
        }
    }


    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

}
