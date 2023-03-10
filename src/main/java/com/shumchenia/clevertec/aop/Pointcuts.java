package com.shumchenia.clevertec.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.DiscountCardRepository.findById(*))")
    public void isDiscountCardFindById() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.DiscountCardRepository.findAll())")
    public void isDiscountCardFindAll() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.DiscountCardRepository.deleteById(*))")
    public void isDiscountCardDeleteById() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.DiscountCardRepository.save(*))")
    public void isDiscountCardSave() {
    }@Pointcut("execution(public * com.shumchenia.clevertec.repository.ProductRepository.findById(*))")
    public void isProductFindById() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.ProductRepository.findAll())")
    public void isProductFindAll() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.ProductRepository.deleteById(*))")
    public void isProductDeleteById() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.ProductRepository.save(*))")
    public void isProductSave() {
    }
}
