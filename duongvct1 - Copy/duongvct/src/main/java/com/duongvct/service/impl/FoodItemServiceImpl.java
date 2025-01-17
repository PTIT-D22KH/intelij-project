package com.duongvct.service.impl;

import com.duongvct.entity.FoodItem;
import com.duongvct.repository.FoodItemRepository;
import com.duongvct.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    @Override
    public List<FoodItem> searchFoodItems(String searchColumn, String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return foodItemRepository.findAll();
        }
        switch (searchColumn) {
            case "id":
                FoodItem item = foodItemRepository.findById(Long.parseLong(searchValue)).orElse(null);
                return item != null ? Collections.singletonList(item) : Collections.emptyList();
            case "name":
                return foodItemRepository.findByNameContaining(searchValue);
            case "category":
                return foodItemRepository.findByCategoryNameContaining(searchValue);
            case "unitName":
                return foodItemRepository.findByUnitNameContaining(searchValue);
            default:
                return foodItemRepository.findAll();
        }
    }
    @Override
    public Page<FoodItem> findAll(Pageable pageable) {
        return foodItemRepository.findAll(pageable);
    }

    @Override
    public Page<FoodItem> searchFoodItems(String searchColumn, String searchValue, Pageable pageable) {
        if (searchValue == null || searchValue.isEmpty()) {
            return foodItemRepository.findAll(pageable);
        }
        switch (searchColumn) {
            case "id":
                return foodItemRepository.findById(Long.parseLong(searchValue), pageable);
            case "name":
                return foodItemRepository.findByNameContaining(searchValue, pageable);
            case "category":
                return foodItemRepository.findByCategoryNameContaining(searchValue, pageable);
            case "unitName":
                return foodItemRepository.findByUnitNameContaining(searchValue, pageable);
            default:
                return foodItemRepository.findAll(pageable);
        }
    }
}