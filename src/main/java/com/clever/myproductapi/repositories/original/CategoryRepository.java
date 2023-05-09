package com.clever.myproductapi.repositories.original;

import com.clever.myproductapi.entities.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);
    Page<Category> findByIsDeletedFalse(Pageable pageable);
    @Query("SELECT c FROM Category c WHERE c.isDeleted = false ORDER BY c.id DESC")
    List<Category> findAllProductsNotDeleted();




}
