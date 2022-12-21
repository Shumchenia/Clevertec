package com.shumchenia.clevertec.service;

import com.shumchenia.clevertec.dto.ProductCreateEditDto;
import com.shumchenia.clevertec.dto.ProductReadDto;
import com.shumchenia.clevertec.mapper.ProductCreateEditMapper;
import com.shumchenia.clevertec.mapper.ProductReadMapper;
import com.shumchenia.clevertec.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReadMapper productReadMapper;
    private final ProductCreateEditMapper productCreateEditMapper;

    public List<ProductReadDto> findAll() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .toList();
    }

    public Optional<ProductReadDto> findById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);
    }

    @Transactional
    public ProductReadDto create(ProductCreateEditDto productCreateEditDto) {
        return Optional.of(productCreateEditDto)
                .map(productCreateEditMapper::map)
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductReadDto> update(Long id, ProductCreateEditDto productCreateEditDto) {
        return productRepository.findById(id)
                .map(entity -> productCreateEditMapper.map(productCreateEditDto, entity))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return productRepository.findById(id)
                .map(entity ->
                {
                    productRepository.deleteById(id);
                    return true;
                })
                .orElse(false);

    }

}
