package com.szabodev.example.rest.repository;

import com.szabodev.example.rest.domain.shop.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByName(String name);

}
