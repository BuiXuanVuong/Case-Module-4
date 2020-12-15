package com.mvc.service.impl;

import com.mvc.converter.CategoryConverter;
import com.mvc.entity.CategoryEntity;
import com.mvc.entity.NewEntity;
import com.mvc.repository.CategoryRepository;
import com.mvc.repository.NewRepository;
import com.mvc.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private NewRepository newRepository;


    @Override
    public Map<String, String> findAll() {
        Map<String, String> result = new HashMap<>();
        List<CategoryEntity> entities = categoryRepository.findAll();
        for (CategoryEntity item : entities) {
            result.put(item.getCode(), item.getName());
        }
        return result;
    }

    @Override
    public List<String> loadMenu() {
        List<String> menu = new ArrayList<>();
        List<CategoryEntity> entities = categoryRepository.findAll();
        for (CategoryEntity item : entities) {
            menu.add(item.getName());
        }
        return menu;
    }

    @Override
    public CategoryEntity findById(Long id) {
        CategoryEntity category= categoryRepository.findOneById(id);
        return category;
    }


    @Override
    public CategoryEntity findByCode(String code) {
        CategoryEntity category = categoryRepository.findOneByCode(code);
        return category;
    }

    @Transactional
    @Override
    public CategoryEntity save(CategoryEntity category) {
        return categoryRepository.save(category);

    }

    @Transactional
    @Override
    public void delete(long[] ids) {
        for (Long id : ids){
            categoryRepository.deleteById(id);
        }

    }

    @Override
    public void remove(CategoryEntity category) {
        categoryRepository.deleteById(category.getId());
    }


    @Override
    public List<CategoryEntity> listCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<CategoryEntity> findAllByNameContaining(String name, Pageable pageable) {
        return categoryRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public Page<CategoryEntity> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
}
