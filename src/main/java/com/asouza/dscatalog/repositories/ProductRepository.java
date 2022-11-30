package com.asouza.dscatalog.repositories;

import com.asouza.dscatalog.entities.Category;
import com.asouza.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
