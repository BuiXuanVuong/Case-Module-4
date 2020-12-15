package com.mvc.converter;

import com.mvc.dto.CategoryDTO;
import com.mvc.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {


    public CategoryDTO toDto(CategoryEntity entity) {
        CategoryDTO result = new CategoryDTO();
        result.setId(entity.getId());
        result.setCode(entity.getCode());
        result.setName(entity.getName());
        result.setModifiedDate(entity.getModifiedDate());
        result.setModifiedBy(entity.getModifiedBy());
        result.setCreatedDate(entity.getCreatedDate());
        result.setCreatedBy(entity.getCreatedBy());
        return result;
    }

    public CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity result = new CategoryEntity();
        result.setCode(dto.getCode());
        result.setName(dto.getName());
        return result;
    }

}
