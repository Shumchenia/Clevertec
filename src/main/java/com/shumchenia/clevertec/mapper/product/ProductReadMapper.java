package com.shumchenia.clevertec.mapper.product;

import com.shumchenia.clevertec.dto.product.ProductReadDto;
import com.shumchenia.clevertec.mapper.Mapper;
import com.shumchenia.clevertec.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    @Override
    public ProductReadDto map(Product object) {
        return new ProductReadDto(
                object.getId(),
                object.getPrice(),
                object.getName()
        );
    }
}
