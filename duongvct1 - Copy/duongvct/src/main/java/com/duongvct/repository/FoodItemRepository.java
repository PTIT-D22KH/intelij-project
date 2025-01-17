package com.duongvct.repository;

import com.duongvct.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> , PagingAndSortingRepository<FoodItem, Long> {
    public List<FoodItem> findByCategoryId(Long categoryId);

    public List<FoodItem> findByNameContaining(String searchValue);

    public List<FoodItem> findByCategoryNameContaining(String searchValue);

    public List<FoodItem> findByUnitNameContaining(String searchValue);

    public Page<FoodItem> findByNameContaining(String searchValue, Pageable pageable);

    public Page<FoodItem> findByCategoryNameContaining(String searchValue, Pageable pageable);

    public Page<FoodItem> findByUnitNameContaining(String searchValue, Pageable pageable);

    public Page<FoodItem> findById(Long id, Pageable pageable);
}
