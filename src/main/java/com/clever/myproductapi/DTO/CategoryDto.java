package com.clever.myproductapi.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1908174172367874180L;

    private Long id;
    private String name;
    private String description;
    private String slug;
    private Date dateOfCreation;
    private Date dateOfModification;
    private Boolean isDeleted;

}
