package com.shumchenia.clevertec.service;

import com.shumchenia.clevertec.dto.product.ProductCreateEditDto;
import com.shumchenia.clevertec.dto.product.ProductReadDto;
import com.shumchenia.clevertec.mapper.product.ProductCreateEditMapper;
import com.shumchenia.clevertec.mapper.product.ProductReadMapper;
import com.shumchenia.clevertec.repository.ProductRepository;
import com.shumchenia.clevertec.util.product.ProductNotFoundException;
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


    /***
     * method for getting all product from repository
     * @return List
     */
    public List<ProductReadDto> findAll() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .toList();
    }

    /***
     * method for getting product by id from repository
     * @param id
     * @return ProductReadDto
     */
    public ProductReadDto findById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::map)
                .orElseThrow(ProductNotFoundException::new);
    }


    /***
     * method for creating product
     * @param productCreateEditDto
     * @return ProductReadDto
     */
    @Transactional
    public ProductReadDto create(ProductCreateEditDto productCreateEditDto) {
        return Optional.of(productCreateEditDto)
                .map(productCreateEditMapper::map)
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    /***
     * method for update product by id
     * @param id
     * @param productCreateEditDto
     * @return ProductReadDto
     */
    @Transactional
    public ProductReadDto update(Long id, ProductCreateEditDto productCreateEditDto) {
        return productRepository.findById(id)
                .map(entity -> productCreateEditMapper.map(productCreateEditDto, entity))
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow(ProductNotFoundException::new);
    }

    /***
     * method for deleting product by id
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);

    }
}
