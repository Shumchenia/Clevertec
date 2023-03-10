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


    /***
     * method for getting all discount card from repository
     * @return List
     */
    public List<DiscountCardReadDto> findAll() {
        return discountCardRepository.findAll().stream()
                .map(discountCardReadMapper::map)
                .toList();
    }

    /***
     * method for getting discount card by id from repository
     * @param id
     * @return DiscountCardReadDto
     */
    public DiscountCardReadDto findById(Long id) {
        return discountCardRepository.findById(id)
                .map(discountCardReadMapper::map)
                .orElseThrow(DiscoundCardNotFoundException::new);
    }


    /***
     * method for creating discount card
     * @param discountCardCreateEditDto
     * @return DiscountCardReadDto
     */
    @Transactional
    public DiscountCardReadDto create(DiscountCardCreateEditDto discountCardCreateEditDto) {
        return Optional.of(discountCardCreateEditDto)
                .map(discountCardCreateEditMapper::map)
                .map(discountCardRepository::save)
                .map(discountCardReadMapper::map)
                .orElseThrow();
    }

    /***
     * method for update discount card by id
     * @param id
     * @param discountCardCreateEditDto
     * @return DiscountCardReadDto
     */
    @Transactional
    public DiscountCardReadDto update(Long id, DiscountCardCreateEditDto discountCardCreateEditDto) {
        return discountCardRepository.findById(id)
                .map(entity -> discountCardCreateEditMapper.map(discountCardCreateEditDto, entity))
                .map(discountCardRepository::save)
                .map(discountCardReadMapper::map)
                .orElseThrow(DiscoundCardNotFoundException::new);
    }

    /***
     * method for deleting discount card by id
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        discountCardRepository.findById(id)
                .orElseThrow(DiscoundCardNotFoundException::new);
        discountCardRepository.deleteById(id);

    }
}
