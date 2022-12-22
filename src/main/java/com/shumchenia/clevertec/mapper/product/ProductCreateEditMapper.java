package com.shumchenia.clevertec.mapper.product;

import com.shumchenia.clevertec.dto.product.ProductCreateEditDto;
import com.shumchenia.clevertec.mapper.Mapper;
import com.shumchenia.clevertec.model.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, Product> {

    @Override
    public Product map(ProductCreateEditDto fromObject, Product toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Product map(ProductCreateEditDto object) {
        Product product = new Product();
        copy(object, product);
        return product;
    }

    private void copy(ProductCreateEditDto object, Product product) {
        product.setName(object.getName());
        product.setPrice(object.getPrice());
    }
}
