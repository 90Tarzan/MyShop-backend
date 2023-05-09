package com.clever.myproductapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7388003339806397251L;
    private Long id;
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Boolean admin;
    private String encryptedPassword;
}
