package com.clever.myproductapi.consumers;

import com.clever.myproductapi.entities2.Category;
import com.clever.myproductapi.repositories.kafkarepo.CategoryKafkaRepo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CategoryConsumer {


    private CategoryKafkaRepo kafkaRepo;
    private KafkaTemplate<String, Category> kafkaTemplate;

    public CategoryConsumer(CategoryKafkaRepo kafkaRepo, KafkaTemplate<String, Category> kafkaTemplate) {
        this.kafkaRepo = kafkaRepo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "prod",groupId = "groupId", containerFactory = "secondKafkaListenerContainerFactory")
    public void receiveMessage(Category category){
        kafkaRepo.save(category);
    }
}

