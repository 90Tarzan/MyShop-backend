package com.clever.myproductapi.services.Impl;

import com.clever.myproductapi.entities.Category;
import com.clever.myproductapi.entities.Product;
import com.clever.myproductapi.exceptions.CategoryException;
import com.clever.myproductapi.exceptions.ProductException;
import com.clever.myproductapi.repositories.original.CategoryRepository;
import com.clever.myproductapi.repositories.original.ProductRepository;
import com.clever.myproductapi.responses.CategoryErrorMessages;
import com.clever.myproductapi.responses.ProductErrorMessages;
import com.clever.myproductapi.services.ProductService;
import com.clever.myproductapi.DTO.ProductDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product checkProduct =  productRepository.findByName(productDto.getName());

        if(checkProduct != null)
            throw new ProductException(ProductErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        Product product = mapper.map(productDto, Product.class);

        product.setDateOfCreation(new Date());
        product.setIsDeleted(false);

        Category category = categoryRepository.getReferenceById(productDto.getCategoryId());
        product.setCategory(category);

        Product newProduct = productRepository.save(product);

        return mapper.map(newProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> allProducts(int page, int limit) {
        if (page > 0) {
            page -= 1;
        }

        List<ProductDto> productDtos = new ArrayList<>();
        PageRequest pageable = PageRequest.of(page, limit);
        Page<Product> products = productRepository.findByIsDeletedFalse(pageable);
        for (Product product : products) {
            ProductDto productDto = mapper.map(product, ProductDto.class);
          Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
            category.ifPresent(value -> productDto.setCategoryId(value.getId()));
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductException( "ID: "+id+" "+ ProductErrorMessages.NO_RECORD_FOUND.getErrorMessage() );
        }
        return mapper.map(product, ProductDto.class);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productDto.getId() + " not found"));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category with id " +productDto.getCategoryId() + " not found"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setDateOfModification(new Date());
        if(product.getDateOfCreation() == null){
            product.setIsDeleted(false);
            product.setDateOfCreation(product.getDateOfModification());
        }

        Product updatedProduct = productRepository.save(product);
        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void isDeletedProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));

        product.setIsDeleted(true);
        product.setDateOfModification(new Date());

        Product deletedProduct = productRepository.save(product);
        mapper.map(deletedProduct, ProductDto.class);
    }

    @Override
    public Page<Product> getAllCategoriesWithPage(int page, int size) {
        return productRepository.findByIsDeletedFalse(PageRequest.of(page,size));
    }


}
