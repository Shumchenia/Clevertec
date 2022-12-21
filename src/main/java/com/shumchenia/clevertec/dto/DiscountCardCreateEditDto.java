package com.shumchenia.clevertec.dto;

import lombok.Value;

@Value
public class DiscountCardCreateEditDto {
    String code;
    Double percent;
}
