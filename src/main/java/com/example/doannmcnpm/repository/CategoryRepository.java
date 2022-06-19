package com.example.doannmcnpm.repository;

import com.example.doannmcnpm.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Integer> {
}
