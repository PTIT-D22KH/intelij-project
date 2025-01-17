package com.duongvct.service;

import com.duongvct.entity.FoodCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodCategoryService {
    List<FoodCategory> findAll();
    FoodCategory findById(Long id);
    void save(FoodCategory category);
    void deleteById(Long id);

    public List<FoodCategory> searchFoodCategories(String searchColumn, String searchValue);

    public Page<FoodCategory> findAll(Pageable pageable);
    Page<FoodCategory> searchFoodCategories(String searchColumn, String searchValue, Pageable pageable);
}