package com.duongvct.service.impl;

import com.duongvct.entity.FoodCategory;
import com.duongvct.repository.FoodCategoryRepository;
import com.duongvct.service.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodCategoryServiceImpl implements FoodCategoryService {

    @Autowired
    private FoodCategoryRepository foodCategoryRepository;

    @Override
    public List<FoodCategory> findAll() {
        return foodCategoryRepository.findAll();
    }

    @Override
    public FoodCategory findById(Long id) {
        return foodCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public void save(FoodCategory category) {
        category.setSlug();
        foodCategoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        foodCategoryRepository.deleteById(id);
    }


    @Override
    public List<FoodCategory> searchFoodCategories(String searchColumn, String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return foodCategoryRepository.findAll();
        }
        switch (searchColumn) {
            case "id":
                FoodCategory category = foodCategoryRepository.findById(Long.parseLong(searchValue)).orElse(null);
                return category != null ? List.of(category) : List.of();
            case "name":
                return foodCategoryRepository.findByNameContaining(searchValue);
            case "slug":
                return foodCategoryRepository.findBySlugContaining(searchValue);
            default:
                return foodCategoryRepository.findAll();
        }
    }
    @Override
    public Page<FoodCategory> findAll(Pageable pageable) {
        return foodCategoryRepository.findAll(pageable);
    }

    @Override
    public Page<FoodCategory> searchFoodCategories(String searchColumn, String searchValue, Pageable pageable) {
        switch (searchColumn) {
            case "name":
                return foodCategoryRepository.findByNameContaining(searchValue, pageable);
            case "slug":
                return foodCategoryRepository.findBySlugContaining(searchValue, pageable);
            default:
                return foodCategoryRepository.findAll(pageable);
        }
    }
}