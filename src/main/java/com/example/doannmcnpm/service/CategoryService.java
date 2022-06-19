package com.example.doannmcnpm.service;


import com.example.doannmcnpm.model.Category;
import com.example.doannmcnpm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

//    @Autowired
//    SoccerFieldRepository soccerFieldRepository;

    public  boolean findById(int categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }

    public void createCategory(Category category)    {
        categoryRepository.save(category);
    }

    public List<Category> listCategory() {
        return  categoryRepository.findAll();
    }

    public void editCategory(int categoryId, Category updateCategory) {
        Category category=categoryRepository.getById(categoryId);
        category.setCategoryName(updateCategory.getCategoryName());
        category.setDescription(updateCategory.getDescription());
        categoryRepository.save(category);
    }

    public void deleteCaterory(int categoryId) {
//        Category category=categoryRepository.getById(categoryId);
        categoryRepository.deleteById(categoryId);
    }
}
