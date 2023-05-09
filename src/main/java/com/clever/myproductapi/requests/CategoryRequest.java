package com.clever.myproductapi.requests;

import com.clever.myproductapi.responses.ProductResponse;
import lombok.Data;

import java.util.List;

@Data
public class CategoryRequest {

    private String name;
    private String description;
    private String slug;
}
