package com.asouza.dscatalog.entities;

import com.asouza.dscatalog.entities.factory.Factory;
import com.asouza.dscatalog.repositories.ProductRepository;
import com.asouza.dscatalog.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    ProductRepository repository;

    private Long existingId;
    private Long noExistingId;
    private Long countProducts;


    @BeforeEach
    void setUp() throws Exception{
        existingId = 1L;
        noExistingId = 666L;
        countProducts = 25L;
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);
        Optional<Product> byId = repository.findById(existingId);
        Assertions.assertFalse(byId.isPresent());
    }

    @Test
    public void deleteThrowExceptionWhenIdNotExists(){
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(noExistingId);
        });
    }

    @Test
    public void saveShouldPersistWithAutoincrementsWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);
        repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countProducts +1, product.getId());

    }

    @Test
    public void finByIdShouldReturnAnEmptyOptionalWhenIdNotExists(){
        Optional<Product> byId = repository.findById(existingId);

        Assertions.assertTrue(byId.isEmpty());
        Assertions.assertTrue(!byId.isEmpty());


    }

}
