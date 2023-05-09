package com.clever.myproductapi.services;


import com.clever.myproductapi.DTO.CategoryDto;
import com.clever.myproductapi.entities.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    Page<Category> getAllCategoriesWithPage(int page, int size);

    List<Category> findAllCategoriesNotDeleted();

    CategoryDto getCategory(String name) throws Exception;

    CategoryDto getCategoryById(Long id) throws Exception;
    CategoryDto updateCategory(Long id, CategoryDto categoryDto) throws Exception;

    void isDeletedCategory(Long id) throws Exception;

}
