package com.shumchenia.clevertec.mapper;

import com.shumchenia.clevertec.dto.DiscountCardCreateEditDto;
import com.shumchenia.clevertec.model.DiscountCard;
import org.springframework.stereotype.Component;
@Component
public class DiscountCardCreateEditMapper implements Mapper<DiscountCardCreateEditDto,DiscountCard> {

    @Override
    public DiscountCard map(DiscountCardCreateEditDto object) {
        DiscountCard discountCard=new DiscountCard();
        copy(object,discountCard);
        return discountCard;
    }

    @Override
    public DiscountCard map(DiscountCardCreateEditDto fromObject, DiscountCard toObject) {
        copy(fromObject,toObject);
        return toObject;
    }

    private void copy(DiscountCardCreateEditDto object, DiscountCard discountCard) {
        discountCard.setCode(object.getCode());
        discountCard.setPercent(object.getPercent());
    }
}
