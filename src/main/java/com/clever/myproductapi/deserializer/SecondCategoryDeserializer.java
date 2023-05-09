package com.clever.myproductapi.deserializer;

import com.clever.myproductapi.entities2.Category;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class SecondCategoryDeserializer extends JsonDeserializer<Category> {

    public SecondCategoryDeserializer() {
        super(Category.class);
 }
}
