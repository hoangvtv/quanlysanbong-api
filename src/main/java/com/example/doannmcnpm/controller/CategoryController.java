package com.example.doannmcnpm.controller;


import com.example.doannmcnpm.common.ApiResponse;
import com.example.doannmcnpm.model.Category;
import com.example.doannmcnpm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    private static ValidatorFactory valFac= Validation.buildDefaultValidatorFactory();


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category)    {

        Validator val=valFac.getValidator();

        Set<ConstraintViolation<Category>> validations=val.validate(category);
        for(ConstraintViolation<Category> validation : validations)	{
            System.out.println(validation.getMessage());
        }

        if(validations.size()==0) {
            categoryService.createCategory(category);
            return new  ResponseEntity<>(new ApiResponse(true, "A new category created"), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new ApiResponse(false,"A new category is not created"),HttpStatus.BAD_REQUEST );
    }

    @GetMapping("/list")
    public List<Category> listCategory()    {
        return categoryService.listCategory();
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> editCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category) {
        if(!categoryService.findById(categoryId)) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exits"), HttpStatus.NOT_FOUND);
        }

        Validator val=valFac.getValidator();
        Set<ConstraintViolation<Category>> validations=val.validate(category);
        for(ConstraintViolation<Category> validation : validations)	{
            System.out.println(validation.getMessage());
        }

        if(validations.size()==0)   {
            categoryService.editCategory(categoryId, category);
            return new ResponseEntity<>(new ApiResponse(true,"Category has been updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false,"A new category is not updated"),HttpStatus.BAD_REQUEST );
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int categoryId)   {
        if(!categoryService.findById(categoryId)) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exits"), HttpStatus.NOT_FOUND);
        }
        categoryService.deleteCaterory(categoryId);
        return new ResponseEntity<>(new ApiResponse(true, "Category has been deleted"), HttpStatus.OK);
    }


}
