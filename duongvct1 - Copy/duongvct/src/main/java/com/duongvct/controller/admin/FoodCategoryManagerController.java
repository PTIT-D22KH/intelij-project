package com.duongvct.controller.admin;

import com.duongvct.entity.FoodCategory;
import com.duongvct.service.impl.FoodCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/food-category")
public class FoodCategoryManagerController {

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

    @GetMapping("")
    public String showFoodCategories(Model model) {
        List<FoodCategory> categories = foodCategoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/food-category/food-category-management";
    }

    @GetMapping("/add")
    public String addFoodCategoryForm(Model model) {
        model.addAttribute("category", new FoodCategory());
        return "admin/food-category/add-food-category";
    }

    @PostMapping("/add")
    public String addFoodCategory(@ModelAttribute FoodCategory category, Model model) {
        foodCategoryService.save(category);
        return "redirect:/admin/food-category";
    }

    @GetMapping("/edit/{id}")
    public String editFoodCategory(@PathVariable Long id, Model model) {
        FoodCategory category = foodCategoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/food-category/edit-food-category";
    }

    @PostMapping("/edit/{id}")
    public String updateFoodCategory(@ModelAttribute FoodCategory category, @PathVariable Long id) {
        FoodCategory existingCategory = foodCategoryService.findById(id);
        existingCategory.setName(category.getName());
        foodCategoryService.save(existingCategory);
        return "redirect:/admin/food-category";
    }

    @PostMapping("/delete")
    public String deleteSelected(@RequestParam("selectedIds") List<Long> selectedIds) {
        for (Long id : selectedIds) {
            foodCategoryService.deleteById(id);
        }
        return "redirect:/admin/food-category";
    }

    @GetMapping("/delete/{id}")
    public String deleteFoodCategory(@PathVariable Long id) {
        foodCategoryService.deleteById(id);
        return "redirect:/admin/food-category";
    }
}