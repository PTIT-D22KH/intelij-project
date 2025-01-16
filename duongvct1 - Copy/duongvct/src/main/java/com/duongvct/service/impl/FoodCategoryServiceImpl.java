package com.duongvct.service.impl;

import com.duongvct.entity.FoodCategory;
import com.duongvct.repository.FoodCategoryRepository;
import com.duongvct.service.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
}