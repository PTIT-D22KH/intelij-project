package com.duongvct.service;

import com.duongvct.entity.FoodCategory;

import java.util.List;

public interface FoodCategoryService {
    List<FoodCategory> findAll();
    FoodCategory findById(Long id);
    void save(FoodCategory category);
    void deleteById(Long id);
}