package com.duongvct.service;

import com.duongvct.entity.Reader;
import com.duongvct.repository.BookRepository;
import com.duongvct.repository.ReaderRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader getReaderById(int id) {
        return readerRepository.findById(id).orElse(null);
    }

    public void updateReader(int id, Reader reader) {
        Reader existingReader = readerRepository.findById(id).orElse(null);
        if (existingReader != null) {
            existingReader.setReaderName(reader.getReaderName());
            existingReader.setBooks(reader.getBooks());
            readerRepository.save(existingReader);
        }
    }

    public void addReader(Reader reader) {
        readerRepository.save(reader);
    }

    public void deleteReader(int id) {
        readerRepository.deleteById(id);
    }

    public void addBookToReader(int readerId, int bookId) {
        Reader reader = readerRepository.findById(readerId).orElse(null);
        if (reader != null) {
            reader.getBooks().add(bookRepository.findById(bookId).orElse(null));
            readerRepository.save(reader);
        }
    }
}
