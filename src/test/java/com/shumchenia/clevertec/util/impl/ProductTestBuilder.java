package com.shumchenia.clevertec.util.impl;

import com.shumchenia.clevertec.model.product.Product;
import com.shumchenia.clevertec.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.With;

@AllArgsConstructor
@With
public class ProductTestBuilder implements TestBuilder<Product> {

    private Long id=1L;
    private Double price = 100.0;
    private String name = "banana";

    public ProductTestBuilder() {
    }

    @Override
    public Product build() {
        final var product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        return product;
    }
}
