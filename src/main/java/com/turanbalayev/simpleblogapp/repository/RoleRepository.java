package com.turanbalayev.simpleblogapp.repository;

import com.turanbalayev.simpleblogapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
