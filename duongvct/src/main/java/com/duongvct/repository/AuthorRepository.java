package com.duongvct.repository;

import com.duongvct.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("select a from Author a where a.authorName like %:authorName%")
    List<Author> findAuthorByName(@Param("authorName") String authorName);
}
