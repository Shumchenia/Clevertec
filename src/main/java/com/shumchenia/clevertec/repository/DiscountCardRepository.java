package com.shumchenia.clevertec.repository;

import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DiscountCardRepository {
    Map<Long,DiscountCard> map=new HashMap();
    {
        map.put(1L,new DiscountCard(1L,"111", 10.0));
        map.put(2L,new DiscountCard(2L,"112", 11.0));
        map.put(3L,new DiscountCard(3L,"113", 12.0));
        map.put(4L,new DiscountCard(4L,"114", 13.0));
        map.put(5L,new DiscountCard(5L,"115", 14.0));
    }


    public List<DiscountCard> findAll(){
        return map.values().stream().toList();
    }
    public Optional<DiscountCard> findById(Long key){
        return Optional.ofNullable(map.get(key));
    }
    public Optional<DiscountCard> findByCode(String code){
        for(DiscountCard discountCard:map.values()){
            if(code.equals(discountCard.getCode())) return Optional.of(discountCard);
        }
        return Optional.empty();
    }
    public DiscountCard save(DiscountCard card){
        card.setId(new Random().nextLong(10L,100L));
        map.put(card.getId(),card);
        return card;
    }

    public void deleteById(Long id){
        map.remove(id);
    }

}
