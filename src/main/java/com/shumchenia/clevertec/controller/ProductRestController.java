package com.shumchenia.clevertec.controller;

import com.shumchenia.clevertec.dto.product.ProductCreateEditDto;
import com.shumchenia.clevertec.dto.product.ProductReadDto;
import com.shumchenia.clevertec.service.ProductService;
import com.shumchenia.clevertec.util.product.ProductErrorResponse;
import com.shumchenia.clevertec.util.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return productService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductReadDto create(@RequestBody ProductCreateEditDto product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ProductReadDto update(@PathVariable("id") Long id,
                                 @RequestBody ProductCreateEditDto product) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id){
        productService.delete(id);
    }

    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> hadlerException(ProductNotFoundException e){
        ProductErrorResponse response= new ProductErrorResponse(
                "product with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

}


