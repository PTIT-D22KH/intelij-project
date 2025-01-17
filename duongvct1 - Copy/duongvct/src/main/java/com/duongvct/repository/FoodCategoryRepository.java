package com.duongvct.repository;

import com.duongvct.entity.FoodCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> , PagingAndSortingRepository<FoodCategory, Long> {
    List<FoodCategory> findByNameContaining(String searchValue);
    List<FoodCategory> findBySlugContaining(String searchValue);

    public Page<FoodCategory> findByNameContaining(String searchValue, Pageable pageable);
    public Page<FoodCategory> findBySlugContaining(String searchValue, Pageable pageable);
}
