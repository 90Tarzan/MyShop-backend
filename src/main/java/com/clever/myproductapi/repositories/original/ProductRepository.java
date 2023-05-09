package com.clever.myproductapi.repositories.original;

import com.clever.myproductapi.entities.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByIsDeletedFalse(Pageable pageable);
    Product findByName(String name);
}
