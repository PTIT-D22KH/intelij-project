package com.duongvct.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int readerId;
    @Column
    private String readerName;
    

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "reader_book",
            joinColumns = @JoinColumn(name = "readerId"),
            inverseJoinColumns = @JoinColumn(name = "bookId")
    )
    @JsonIgnore
    private List<Book> books;



    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
