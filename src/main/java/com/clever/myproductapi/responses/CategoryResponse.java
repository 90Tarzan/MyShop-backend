package com.clever.myproductapi.responses;

import com.clever.myproductapi.entities.Category;
import com.clever.myproductapi.entities.Product;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private String slug;
    private Boolean isDeleted;
    private Date dateOfCreation;
    private Date dateOfModification;
}
