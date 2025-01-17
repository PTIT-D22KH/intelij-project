package com.duongvct.controller.admin;

import com.duongvct.entity.FoodCategory;
import com.duongvct.service.impl.FoodCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/food-category")
public class FoodCategoryManagerController {

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

//    @GetMapping("")
//    public String showFoodCategories(@RequestParam(value = "searchColumn", required = false) String searchColumn,
//                                     @RequestParam(value = "searchValue", required = false) String searchValue, Model model) {
//        List<FoodCategory> categories;
//        if (searchColumn != null && searchValue != null) {
//            categories = foodCategoryService.searchFoodCategories(searchColumn, searchValue);
//        } else {
//            categories = foodCategoryService.findAll();
//        }
//        model.addAttribute("categories", categories);
//        return "admin/food-category/food-category-management";
//    }
    @GetMapping("")
    public String showFoodCategories(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "2") int size,
                                     @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                                     @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                     @RequestParam(value = "searchColumn", required = false) String searchColumn,
                                     @RequestParam(value = "searchValue", required = false) String searchValue,
                                     Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortField));
        Page<FoodCategory> categories;
        if (searchColumn != null && searchValue != null) {
            categories = foodCategoryService.searchFoodCategories(searchColumn, searchValue, pageable);
        } else {
            categories = foodCategoryService.findAll(pageable);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categories.getTotalPages());
        model.addAttribute("totalItems", categories.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
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