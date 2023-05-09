package com.clever.myproductapi.requests;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private String image;
    private String slug;
    private Long categoryId;
}
