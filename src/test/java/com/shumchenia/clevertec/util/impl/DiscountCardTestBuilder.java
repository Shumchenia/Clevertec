package com.shumchenia.clevertec.util.impl;

import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import com.shumchenia.clevertec.util.TestBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

@AllArgsConstructor
@With
public class DiscountCardTestBuilder implements TestBuilder<DiscountCard> {
    private String code = "1111";

    private Double percent = 10.0;
    private Long id=1L;

    public DiscountCardTestBuilder() {
    }

    @Override
    public DiscountCard build() {
        final var discountCard = new DiscountCard();
        discountCard.setId(id);
        discountCard.setCode(code);
        discountCard.setPercent(percent);
        return discountCard;
    }
}
