package com.shumchenia.clevertec.service;

import com.shumchenia.clevertec.dto.discountCard.DiscountCardCreateEditDto;
import com.shumchenia.clevertec.dto.discountCard.DiscountCardReadDto;
import com.shumchenia.clevertec.mapper.discountCard.DiscountCardCreateEditMapper;
import com.shumchenia.clevertec.mapper.discountCard.DiscountCardReadMapper;
import com.shumchenia.clevertec.repository.DiscountCardRepository;
import com.shumchenia.clevertec.util.discountCard.DiscoundCardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiscountCardService {

    private final DiscountCardRepository discountCardRepository;
    private final DiscountCardReadMapper discountCardReadMapper;
    private final DiscountCardCreateEditMapper discountCardCreateEditMapper;


    public List<DiscountCardReadDto> findAll() {
        return discountCardRepository.findAll().stream()
                .map(discountCardReadMapper::map)
                .toList();
    }

    public DiscountCardReadDto findById(Long id) {
        return discountCardRepository.findById(id)
                .map(discountCardReadMapper::map)
                .orElseThrow(DiscoundCardNotFoundException::new);
    }

    @Transactional
    public DiscountCardReadDto create(DiscountCardCreateEditDto discountCardCreateEditDto) {
        return Optional.of(discountCardCreateEditDto)
                .map(discountCardCreateEditMapper::map)
                .map(discountCardRepository::save)
                .map(discountCardReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public DiscountCardReadDto update(Long id, DiscountCardCreateEditDto discountCardCreateEditDto) {
        return discountCardRepository.findById(id)
                .map(entity -> discountCardCreateEditMapper.map(discountCardCreateEditDto, entity))
                .map(discountCardRepository::save)
                .map(discountCardReadMapper::map)
                .orElseThrow(DiscoundCardNotFoundException::new);
    }

    @Transactional
    public void delete(Long id) {
        discountCardRepository.findById(id)
                .orElseThrow(DiscoundCardNotFoundException::new);
        discountCardRepository.deleteById(id);

    }
}
