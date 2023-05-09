package com.clever.myproductapi.controllers;


import com.clever.myproductapi.DTO.ProductDto;
import com.clever.myproductapi.entities.Product;
import com.clever.myproductapi.exceptions.ProductException;
import com.clever.myproductapi.repositories.original.CategoryRepository;
import com.clever.myproductapi.repositories.original.ProductRepository;
import com.clever.myproductapi.requests.ProductRequest;
import com.clever.myproductapi.responses.CategoryResponse;
import com.clever.myproductapi.responses.ProductErrorMessages;
import com.clever.myproductapi.responses.ProductResponse;
import com.clever.myproductapi.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        if(productRequest.getName().isBlank()
                || productRequest.getDescription().isEmpty()
                || productRequest.getSlug().isEmpty())
            throw new ProductException(ProductErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        // Convert request to DTO
        ProductDto productDto = mapper.map(productRequest, ProductDto.class);
        productDto.setCategoryId(productRequest.getCategoryId());

        // Save product using ProductService
        ProductDto createdProduct = productService.createProduct(productDto);
        ProductResponse productResponse = mapper.map(createdProduct, ProductResponse.class);

        // Return the created product DTO with 201 Created status
        return  new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) throws Exception {
        ProductDto productDto = productService.getProductById(id);
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(productDto, productResponse);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }



    @GetMapping("/allprod")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size) {
        try {
            Page<Product> productPage;
            List<Product> products;
            productPage = productService.getAllCategoriesWithPage(page,size);
            products = productPage.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            response.put("currentPage", productPage.getNumber());
            response.put("totalItems", productPage.getTotalElements());
            response.put("totalPages", productPage.getTotalPages());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductRequest productRequest) {
        ProductDto productDto = mapper.map(productRequest, ProductDto.class);
        productDto.setId(id);

        ProductDto updatedProductDto = productService.updateProduct(id, productDto);

        ProductResponse productResponse = mapper.map(updatedProductDto, ProductResponse.class);
        return ResponseEntity.ok(productResponse);
    }


    @PutMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateDeleteCategory(@PathVariable Long id) throws Exception {
        productService.isDeletedProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}