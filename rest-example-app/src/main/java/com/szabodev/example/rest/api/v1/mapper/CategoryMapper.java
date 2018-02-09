package com.szabodev.example.rest.api.v1.mapper;

import com.szabodev.example.rest.api.v1.model.CategoryDTO;
import com.szabodev.example.rest.domain.shop.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDTO(Category category);
}
