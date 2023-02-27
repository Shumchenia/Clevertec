package com.shumchenia.clevertec.model.check.builder;

import com.shumchenia.clevertec.model.check.Check;
import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import com.shumchenia.clevertec.model.product.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
public class BaseCheckBuilder implements CheckBuilder {

    private Map<Product, Integer> products;
    private Optional<DiscountCard> discountCard;
    private BigDecimal sum;
    @Override
    public CheckBuilder addProduct(Map<Product, Integer> products) {
        this.products = products;
        return this;
    }

    @Override
    public CheckBuilder addDiscountCard(Optional<DiscountCard> discountCard) {
        this.discountCard = discountCard;
        return this;
    }

    @Override
    public Check build() {
        if (products.isEmpty()) {
            return null;
        }
        sum=BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            BigDecimal price = BigDecimal.valueOf((entry.getKey().getPrice()))
                    .multiply(BigDecimal.valueOf(entry.getValue()));
            sum=sum.add(price);
        }

        discountCard.ifPresent(card -> sum=sum.multiply(BigDecimal.valueOf((100 - card.getPercent()) /100)));

        return new Check(products, discountCard, sum);
    }
}
