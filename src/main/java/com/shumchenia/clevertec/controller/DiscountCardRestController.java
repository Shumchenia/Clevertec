package com.shumchenia.clevertec.controller;

import com.shumchenia.clevertec.dto.discountCard.DiscountCardCreateEditDto;
import com.shumchenia.clevertec.dto.discountCard.DiscountCardReadDto;
import com.shumchenia.clevertec.service.DiscountCardService;
import com.shumchenia.clevertec.util.discountCard.DiscoundCardErrorResponse;
import com.shumchenia.clevertec.util.discountCard.DiscoundCardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return discountCardService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountCardReadDto create(@RequestBody DiscountCardCreateEditDto product) {
        return discountCardService.create(product);
    }

    @PutMapping("/{id}")
    public DiscountCardReadDto update(@PathVariable("id") Long id,
                                 @RequestBody DiscountCardCreateEditDto product) {
        return discountCardService.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        discountCardService.delete(id);
    }
    @ExceptionHandler
    private ResponseEntity<DiscoundCardErrorResponse> hadlerException(DiscoundCardNotFoundException e){
        DiscoundCardErrorResponse response= new DiscoundCardErrorResponse(
                "discound card with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}



