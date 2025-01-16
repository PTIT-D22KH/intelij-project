package com.duongvct.editor;

import com.duongvct.service.impl.FoodCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class FoodCategoryEditor extends PropertyEditorSupport {

    @Autowired
    private FoodCategoryServiceImpl foodCategoryService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(foodCategoryService.findById(Long.parseLong(text)));
    }

}
