package com.shumchenia.clevertec.model.check.builder;

import com.shumchenia.clevertec.model.check.Check;
import com.shumchenia.clevertec.model.discoundCard.DiscountCard;
import com.shumchenia.clevertec.model.product.Product;

import java.util.Map;
import java.util.Optional;

public interface CheckBuilder {

    public CheckBuilder addProduct(Map<Product,Integer> products);

    public CheckBuilder addDiscountCard(Optional<DiscountCard> discountCard);

    public Check build();
}
