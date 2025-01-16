package com.duongvct.service.impl;

import com.duongvct.entity.FoodItem;
import com.duongvct.repository.FoodItemRepository;
import com.duongvct.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Override
    public List<FoodItem> findAll() {
        return foodItemRepository.findAll();
    }

    @Override
    public FoodItem findById(Long id) {
        return foodItemRepository.findById(id).orElse(null);
    }

    @Override
    public void save(FoodItem item) {
        foodItemRepository.save(item);
    }

    @Override
    public void deleteById(Long id) {
        foodItemRepository.deleteById(id);
    }

    @Override
    public List<FoodItem> findByCategoryId(Long categoryId) {
        return foodItemRepository.findByCategoryId(categoryId);
    }
}