package com.clever.myproductapi.repositories.kafkarepo;


import com.clever.myproductapi.entities2.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryKafkaRepo extends JpaRepository<Category, Long> {

}
