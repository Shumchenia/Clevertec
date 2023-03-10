package com.shumchenia.clevertec.repository;

import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import com.shumchenia.clevertec.model.product.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {
    Map<Long, Product> map = new HashMap();

    {
        map.put(1L, new Product(1L, 10.0, "Carrot"));
        map.put(2L, new Product(2L, 11.0, "meat"));
        map.put(3L, new Product(3L, 12.0, "milk"));
        map.put(4L, new Product(4L, 13.0, "bread"));
        map.put(5L, new Product(5L, 14.0, "egg"));
    }

    public List<Product> findAll() {
        return  map.values().stream().toList();
    }

    public Optional<Product> findById(Long key) {
        return Optional.ofNullable(map.get(key));
    }

    public Product save(Product product) {
        product.setId(new Random().nextLong(10L,100L));
        map.put(product.getId(), product);
        return product;
    }

    public void deleteById(Long id) {
        map.remove(id);
    }

}
