package com.duongvct.service;

import com.duongvct.entity.FoodItem;

import java.util.List;

public interface FoodItemService {
    List<FoodItem> findAll();
    FoodItem findById(Long id);
    void save(FoodItem item);
    void deleteById(Long id);

    public List<FoodItem> findByCategoryId(Long categoryId);

    public List<FoodItem> searchFoodItems(String searchColumn, String searchValue);


}