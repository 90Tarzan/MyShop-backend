package com.clever.myproductapi.services;

import com.clever.myproductapi.DTO.CategoryDto;
import com.clever.myproductapi.DTO.ProductDto;
import com.clever.myproductapi.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);


    List<ProductDto> allProducts(int page, int limit);

    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long id,ProductDto productDto);

    void isDeletedProduct(Long id);

    Page<Product> getAllCategoriesWithPage(int page, int size);
}
