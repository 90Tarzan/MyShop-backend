package com.clever.myproductapi.repositories.original;

import com.clever.myproductapi.entities.ERole;
import com.clever.myproductapi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
