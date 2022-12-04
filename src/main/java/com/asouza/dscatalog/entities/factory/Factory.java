package com.asouza.dscatalog.entities.factory;

import com.asouza.dscatalog.dto.ProductDTO;
import com.asouza.dscatalog.entities.Category;
import com.asouza.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(2L, "Machine", "For your day.", 200.00, "htto://google.com/jpg", Instant.now());
        product.getCategories().add(new Category(2L, "Electronics"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        Product  product = createProduct();
        ProductDTO productDTO = new ProductDTO(product, product.getCategories());
        return productDTO;
    }
}
