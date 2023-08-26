package com.turanbalayev.simpleblogapp.repository;

import com.turanbalayev.simpleblogapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
