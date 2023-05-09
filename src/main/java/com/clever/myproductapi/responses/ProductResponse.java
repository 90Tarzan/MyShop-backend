package com.clever.myproductapi.responses;

import com.clever.myproductapi.entities.Category;
import lombok.Data;

import java.util.Date;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String image;
    private String slug;
    private Boolean isDeleted;
    private Date dateOfCreation;
    private Date dateOfModification;
    private Long categoryId;

}
