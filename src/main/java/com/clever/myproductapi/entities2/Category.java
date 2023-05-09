package com.clever.myproductapi.entities2;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Entity
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 5552459402735788341L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String slug;
    @Column(nullable = false, name = "date_of_creation")
    private Date dateOfCreation;
    @Column(nullable = false, name = "date_of_modification")
    private Date dateOfModification;
    @Column(nullable = false)
    private Boolean isDeleted;

}