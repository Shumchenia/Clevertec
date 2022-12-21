package com.shumchenia.clevertec.controller;

import com.shumchenia.clevertec.dto.DiscountCardCreateEditDto;
import com.shumchenia.clevertec.dto.DiscountCardReadDto;
import com.shumchenia.clevertec.service.DiscountCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discountCard")
@RequiredArgsConstructor
public class DiscountCardRestController{
    private final DiscountCardService discountCardService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DiscountCardReadDto> findAll() {
        return discountCardService.findAll();
    }

    @GetMapping("/{id}")
    public DiscountCardReadDto findById(@PathVariable("id") Long id) {
        return discountCardService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountCardReadDto create(@RequestBody DiscountCardCreateEditDto product) {
        return discountCardService.create(product);
    }

    @PutMapping("/{id}")
    public DiscountCardReadDto update(@PathVariable("id") Long id,
                                 @RequestBody DiscountCardCreateEditDto product) {
        return discountCardService.update(id, product)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        if(!discountCardService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}



