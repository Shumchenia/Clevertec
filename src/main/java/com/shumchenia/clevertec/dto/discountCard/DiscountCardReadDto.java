package com.shumchenia.clevertec.dto.discountCard;

import lombok.Value;

@Value
public class DiscountCardReadDto {

    Long id;
    String code;
    Double percent;
}
