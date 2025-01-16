package com.duongvct.repository;

import com.duongvct.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    public List<FoodItem> findByCategoryId(Long categoryId);
}
