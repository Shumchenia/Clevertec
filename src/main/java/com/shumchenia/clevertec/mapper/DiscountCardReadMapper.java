package com.shumchenia.clevertec.mapper;

import com.shumchenia.clevertec.dto.DiscountCardReadDto;
import com.shumchenia.clevertec.model.DiscountCard;
import org.springframework.stereotype.Component;

@Component
public class DiscountCardReadMapper implements Mapper<DiscountCard, DiscountCardReadDto>{

     @Override
    public DiscountCardReadDto map(DiscountCard object) {
        return new DiscountCardReadDto(
                object.getId(),
                object.getCode(),
                object.getPercent()
        );
    }
}
