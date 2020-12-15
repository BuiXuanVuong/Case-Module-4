package com.mvc.service;


import com.mvc.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICategoryService {

    Map<String, String> findAll();

    List<String> loadMenu();

    CategoryEntity findById(Long id);

    CategoryEntity findByCode(String code);

    CategoryEntity save(CategoryEntity category);

    void delete (long[] ids);

    void remove(CategoryEntity entity);

    List<CategoryEntity> listCategory();

    Page<CategoryEntity> findAllByNameContaining(String name, Pageable pageable);

    Page<CategoryEntity> findAll(Pageable pageable);
}
