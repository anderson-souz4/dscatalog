package com.asouza.dscatalog.services;

import com.asouza.dscatalog.dto.CategoryDTO;
import com.asouza.dscatalog.entities.Category;
import com.asouza.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> all = categoryRepository.findAll();
        return all.stream().map(category -> new CategoryDTO(category)).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> byId = categoryRepository.findById(id);
        Category entity = byId.get();
        return new CategoryDTO(entity);

    }
}
