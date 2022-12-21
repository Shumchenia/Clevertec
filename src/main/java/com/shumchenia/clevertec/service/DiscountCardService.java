package com.shumchenia.clevertec.service;

import com.shumchenia.clevertec.dto.DiscountCardCreateEditDto;
import com.shumchenia.clevertec.dto.DiscountCardReadDto;
import com.shumchenia.clevertec.mapper.DiscountCardCreateEditMapper;
import com.shumchenia.clevertec.mapper.DiscountCardReadMapper;
import com.shumchenia.clevertec.repository.DiscountCardRepository;
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

    public Optional<DiscountCardReadDto> findById(Long id) {
        return discountCardRepository.findById(id)
                .map(discountCardReadMapper::map);
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
    public Optional<DiscountCardReadDto> update(Long id, DiscountCardCreateEditDto discountCardCreateEditDto) {
        return discountCardRepository.findById(id)
                .map(entity -> discountCardCreateEditMapper.map(discountCardCreateEditDto, entity))
                .map(discountCardRepository::saveAndFlush)
                .map(discountCardReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return discountCardRepository.findById(id)
                .map(entity ->
                {
                    discountCardRepository.deleteById(id);
                    return true;
                })
                .orElse(false);

    }
}
