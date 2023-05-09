package com.clever.myproductapi.entities;

import com.clever.myproductapi.entities.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -987642995759926940L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String image;
    @NotBlank
    private String slug;
    @Column( name = "date_of_creation")
    private Date dateOfCreation;
    @Column( name = "date_of_modification")
    private Date dateOfModification;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @ManyToOne // Many products belong to one category
    @JoinColumn(name = "category_id")
    private Category category;

}
