package com.mvc.repository;

import com.mvc.entity.CategoryEntity;
import com.mvc.entity.NewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findOneByCode(String code);

    CategoryEntity findOneById(Long id);

    Page<CategoryEntity> findAllByNameContaining(String name, Pageable pageable);

}

