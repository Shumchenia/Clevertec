package com.shumchenia.clevertec.controller;

import com.shumchenia.clevertec.dto.ProductCreateEditDto;
import com.shumchenia.clevertec.dto.ProductReadDto;
import com.shumchenia.clevertec.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductReadDto> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductReadDto findById(@PathVariable("id") Long id) {
        return productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductReadDto create(@RequestBody ProductCreateEditDto product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ProductReadDto update(@PathVariable("id") Long id,
                                 @RequestBody ProductCreateEditDto product) {
        return productService.update(id, product)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        if(!productService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}


