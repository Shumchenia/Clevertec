package com.shumchenia.clevertec.repository;

import com.shumchenia.clevertec.model.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {
    Optional<DiscountCard> findByCode(String code);
}
