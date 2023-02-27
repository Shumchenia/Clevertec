package com.shumchenia.clevertec.model.check;

import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import com.shumchenia.clevertec.model.product.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;


@Data
public class Check {

    private Map<Product, Integer> products;
    private Optional<DiscountCard> discountCard;
    private BigDecimal sum;

    public Check() {
    }

    public Check(Map<Product, Integer> products, Optional<DiscountCard> discountCard,BigDecimal sum) {
        this.products = products;
        this.discountCard = discountCard;
        this.sum=sum;
    }

    @Override
    public String toString() {
        return "Check" +
                ""+ products +
                ", discountCard=" + discountCard.get().getPercent()+"%" +
                ", sum=" + sum +
                '}';
    }
}
