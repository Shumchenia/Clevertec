package com.shumchenia.clevertec.mapper;

import com.shumchenia.clevertec.dto.ProductReadDto;
import com.shumchenia.clevertec.model.Product;
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
