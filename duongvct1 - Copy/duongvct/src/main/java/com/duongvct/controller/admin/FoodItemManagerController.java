package com.duongvct.controller.admin;

import com.duongvct.editor.FoodCategoryEditor;
import com.duongvct.entity.FoodCategory;
import com.duongvct.entity.FoodItem;
import com.duongvct.service.impl.FoodCategoryServiceImpl;
import com.duongvct.service.impl.FoodItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FoodItemManagerController {

    @Autowired
    private FoodItemServiceImpl foodItemService;

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

    @Autowired
    private FoodCategoryEditor foodCategoryEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(FoodCategory.class, foodCategoryEditor);
    }

    @GetMapping("/admin/food-item")
    public String showFoodItems(Model model) {
        List<FoodItem> items = foodItemService.findAll();
        model.addAttribute("items", items);
        return "admin/food-item/food-item-management";
    }

    @GetMapping("/admin/food-item/add")
    public String addFoodItemForm(Model model) {
        model.addAttribute("item", new FoodItem());
        model.addAttribute("categories", foodCategoryService.findAll());
        return "admin/food-item/add-food-item.html";
    }

    @PostMapping("/admin/food-item/add")
    public String addFoodItem(@ModelAttribute FoodItem item, Model model) {
        foodItemService.save(item);
        return "redirect:/admin/food-item";
    }

    @GetMapping("/admin/food-item/edit/{id}")
    public String editFoodItem(@PathVariable Long id, Model model) {
        FoodItem item = foodItemService.findById(id);
        model.addAttribute("item", item);
        model.addAttribute("categories", foodCategoryService.findAll());
        return "admin/food-item/edit-food-item";
    }

    @PostMapping("/admin/food-item/edit/{id}")
    public String updateFoodItem(@ModelAttribute FoodItem item, @PathVariable Long id) {
        FoodItem existingItem = foodItemService.findById(id);
        existingItem.setName(item.getName());
        existingItem.setCategory(item.getCategory());
        existingItem.setUnitName(item.getUnitName());
        existingItem.setPrice(item.getPrice());
        foodItemService.save(existingItem);
        return "redirect:/admin/food-item";
    }

    @PostMapping("/admin/food-item/delete")
    public String deleteSelected(@RequestParam("selectedIds") List<Long> selectedIds) {
        for (Long id : selectedIds) {
            foodItemService.deleteById(id);
        }
        return "redirect:/admin/food-item";
    }

    @GetMapping("/admin/food-item/delete/{id}")
    public String deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteById(id);
        return "redirect:/admin/food-item";
    }
}