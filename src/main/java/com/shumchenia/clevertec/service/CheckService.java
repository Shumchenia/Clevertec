package com.shumchenia.clevertec.service;

import com.shumchenia.clevertec.model.check.builder.BaseCheckBuilder;
import com.shumchenia.clevertec.model.check.Check;
import com.shumchenia.clevertec.model.product.DiscountProduct;
import com.shumchenia.clevertec.model.product.Product;
import com.shumchenia.clevertec.repository.DiscountCardRepository;
import com.shumchenia.clevertec.repository.ProductRepository;
import com.shumchenia.clevertec.util.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CheckService {

    private final BaseCheckBuilder checkBuilder;
    private final ProductRepository productRepository;
    private final DiscountCardRepository discountCardRepository;

    @Transactional
    public Check getCheck(List<Integer> productsId, String card) {
        Map<Product, Integer> products = new HashMap<>();

        for (Integer productId : productsId) {

            Product product = productRepository.findById(Long.valueOf(productId))
                    .orElseThrow(ProductNotFoundException::new);
            Integer count = Collections.frequency(productsId, Math.toIntExact(product.getId()));

            if (count > 5) {
                products.put(new DiscountProduct(product,10d), count);
            } else {
                products.put(product, count);
            }
        }
        return checkBuilder
                .addProduct(products)
                .addDiscountCard(discountCardRepository.findByCode(card))
                .build();
    }
}
