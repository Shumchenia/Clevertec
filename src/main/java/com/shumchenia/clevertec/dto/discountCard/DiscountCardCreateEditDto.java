package com.shumchenia.clevertec.dto.discountCard;

import lombok.Value;

@Value
public class DiscountCardCreateEditDto {
    String code;
    Double percent;
}
