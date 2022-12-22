package com.shumchenia.clevertec.controller;

import com.shumchenia.clevertec.model.check.Check;
import com.shumchenia.clevertec.service.CheckService;
import com.shumchenia.clevertec.util.product.ProductErrorResponse;
import com.shumchenia.clevertec.util.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/check")
@RequiredArgsConstructor
public class CheckRestController {

    private final CheckService checkService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Check getCheck(@RequestParam List<Integer> itemId, @RequestParam(name = "card",required = false) String card ){
        return checkService.getCheck(itemId,card);
    }

    @ExceptionHandler
    private ResponseEntity<ProductErrorResponse> hadlerException(ProductNotFoundException e){
        ProductErrorResponse response= new ProductErrorResponse(
                "product with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
