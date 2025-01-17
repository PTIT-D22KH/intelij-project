package com.duongvct.service;

import com.duongvct.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodItemService {
    List<FoodItem> findAll();
    FoodItem findById(Long id);
    void save(FoodItem item);
    void deleteById(Long id);

    public List<FoodItem> findByCategoryId(Long categoryId);

    public List<FoodItem> searchFoodItems(String searchColumn, String searchValue);

    Page<FoodItem> findAll(Pageable pageable);

    Page<FoodItem> searchFoodItems(String searchColumn, String searchValue, Pageable pageable);
}