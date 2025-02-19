package com.duongvct.controller.admin;

import com.duongvct.editor.FoodCategoryEditor;
import com.duongvct.entity.FoodCategory;
import com.duongvct.entity.FoodItem;
import com.duongvct.service.impl.FoodCategoryServiceImpl;
import com.duongvct.service.impl.FoodItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

//    @GetMapping("/admin/food-item")
//    public String showFoodItems(@RequestParam(value = "searchColumn", required = false) String searchColumn,
//                                @RequestParam(value = "searchValue", required = false) String searchValue, Model model) {
//        List<FoodItem> items;
//        if (searchColumn != null && searchValue != null) {
//            items = foodItemService.searchFoodItems(searchColumn, searchValue);
//        } else {
//            items = foodItemService.findAll();
//        }        model.addAttribute("items", items);
//        return "admin/food-item/food-item-management";
//    }

    @GetMapping("/admin/food-item")
    public String showFoodItems(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "3") int size,
                                @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                                @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
                                @RequestParam(value = "searchColumn", required = false) String searchColumn,
                                @RequestParam(value = "searchValue", required = false) String searchValue,
                                Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortField));
        Page<FoodItem> items;
        if (searchColumn != null && searchValue != null) {
            items = foodItemService.searchFoodItems(searchColumn, searchValue, pageable);
        } else {
            items = foodItemService.findAll(pageable);
        }
        model.addAttribute("items", items);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", items.getTotalPages());
        model.addAttribute("totalItems", items.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
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