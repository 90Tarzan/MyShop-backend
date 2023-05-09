package com.clever.myproductapi.controllers;

import com.clever.myproductapi.DTO.CategoryDto;
import com.clever.myproductapi.entities.Category;
import com.clever.myproductapi.exceptions.CategoryException;
import com.clever.myproductapi.requests.CategoryRequest;
import com.clever.myproductapi.responses.CategoryErrorMessages;
import com.clever.myproductapi.responses.CategoryResponse;
import com.clever.myproductapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private KafkaTemplate<String, com.clever.myproductapi.entities2.Category> kafkaTemplate;

    @Autowired
    private ModelMapper mapper;

    @PostMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {

        if(categoryRequest.getName().isBlank()
                || categoryRequest.getDescription().isEmpty()
                || categoryRequest.getSlug().isEmpty())
            throw new CategoryException(CategoryErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(categoryRequest, categoryDto);

        // PRESENTATION LAYER
        CategoryDto createCategory = categoryService.createCategory(categoryDto);
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(createCategory, categoryResponse);

        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) throws Exception {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        CategoryResponse categoryResponse = new CategoryResponse();
        BeanUtils.copyProperties(categoryDto, categoryResponse);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }



    @GetMapping("/allCat")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getCategories(
             @RequestParam(name = "page", defaultValue = "0") int page,
             @RequestParam(name = "size", defaultValue = "8") int size) {
        try {
            Page<Category> categoryPage;
            List<Category> categories;
            categoryPage = categoryService.getAllCategoriesWithPage(page,size);
            categories = categoryPage.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("categories", categories);
            response.put("currentPage", categoryPage.getNumber());
            response.put("totalItems", categoryPage.getTotalElements());
            response.put("totalPages", categoryPage.getTotalPages());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping("/notDeleted")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Category> categoriesNotDeleted() {
        return categoryService.findAllCategoriesNotDeleted();
    }


    @PutMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateDeleteCategory(@PathVariable Long id) throws Exception {
        categoryService.isDeletedCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest)
            throws Exception {

        CategoryDto categoryDto = mapper.map(categoryRequest, CategoryDto.class);

        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);

        CategoryResponse categoryResponse = mapper.map(updatedCategory, CategoryResponse.class);
        return new ResponseEntity<>(categoryResponse, HttpStatus.ACCEPTED);

    }
}