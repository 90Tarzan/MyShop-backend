package com.clever.myproductapi.DTO;

import com.clever.myproductapi.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 6667828061606874285L;

    private Long id;
    private String name;
    private String description;
    private String image;
    private String slug;
    private Date dateOfCreation;
    private Date dateOfModification;
    private Boolean isDeleted;
    private Long categoryId;


}
