package com.shumchenia.clevertec.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.*Repository.findById(*))")
    public void isFindById() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.*Repository.findAll())")
    public void isFindAll() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.*Repository.deleteById(*))")
    public void isDeleteById() {
    }
    @Pointcut("execution(public * com.shumchenia.clevertec.repository.*Repository.save(*))")
    public void isSave() {
    }
}
