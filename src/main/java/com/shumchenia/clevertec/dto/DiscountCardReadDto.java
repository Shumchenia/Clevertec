package com.shumchenia.clevertec.dto;

import lombok.Value;

@Value
public class DiscountCardReadDto {

    Long id;
    String code;
    Double percent;
}
