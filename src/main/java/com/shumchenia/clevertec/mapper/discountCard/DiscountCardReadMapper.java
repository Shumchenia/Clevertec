package com.shumchenia.clevertec.mapper.discountCard;

import com.shumchenia.clevertec.dto.discountCard.DiscountCardReadDto;
import com.shumchenia.clevertec.mapper.Mapper;
import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import org.springframework.stereotype.Component;

@Component
public class DiscountCardReadMapper implements Mapper<DiscountCard, DiscountCardReadDto> {

     @Override
    public DiscountCardReadDto map(DiscountCard object) {
        return new DiscountCardReadDto(
                object.getId(),
                object.getCode(),
                object.getPercent()
        );
    }
}
