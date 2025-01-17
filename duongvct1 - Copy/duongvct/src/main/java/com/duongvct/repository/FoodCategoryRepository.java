package com.duongvct.repository;

import com.duongvct.entity.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
    List<FoodCategory> findByNameContaining(String searchValue);
    List<FoodCategory> findBySlugContaining(String searchValue);
}
