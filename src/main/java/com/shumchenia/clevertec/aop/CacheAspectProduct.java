package com.shumchenia.clevertec.aop;


import com.shumchenia.clevertec.cache.Cache;
import com.shumchenia.clevertec.model.product.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspectProduct {

    @Value("${cache.algorithm}")
    public String algorithm;

    private Cache<Long, Product> cache;

    private final BeanFactory beanFactory;

    @PostConstruct
    public void setAlgorithm() {
        this.cache = beanFactory.getBean(algorithm, Cache.class);
    }

    /***
     * cache Advice to findByID
     * @param point
     * @param id
     * @return Optional
     * @throws Throwable
     */
    @Around("com.shumchenia.clevertec.aop.Pointcuts.isProductFindById()" +
            "&& args(id)")
    public Object cachingFindById(ProceedingJoinPoint point, Object id) throws Throwable {
        if (cache.get((Long) id).isPresent()) {
            return cache.get((Long) id);
        }
        Optional<Product> product = (Optional<Product>) point.proceed();
        product.ifPresent(card -> cache.put((Long) id, card));
        return product;
    }

    /***
     * cache Advice to findAll
     * @param point
     * @return List
     * @throws Throwable
     */
    @Around("com.shumchenia.clevertec.aop.Pointcuts.isProductFindAll()")
    public Object cachingFindAll(ProceedingJoinPoint point) throws Throwable {
        List<Product> list = (List<Product>) point.proceed();
        list.stream().forEach(x->cache.put((Long) x.getId(),x));
        return list;
    }

    /***
     * cache Advice for deleteById
     * @param point
     * @param id
     * @throws Throwable
     */
    @Around("com.shumchenia.clevertec.aop.Pointcuts.isProductDeleteById()" +
            "&& args(id)")
    public void cachingDeleteById(ProceedingJoinPoint point, Object id) throws Throwable {
        if (cache.get((Long) id).isPresent()) {
            cache.remove((Long) id);
        }
        point.proceed();
    }

    /***
     * cache Advice for save
     * @param point
     * @throws Throwable
     */
    @Around("com.shumchenia.clevertec.aop.Pointcuts.isProductSave()")
    public void cachingSave(ProceedingJoinPoint point) throws Throwable {
        Optional<Product> entity = (Optional<Product>) point.proceed();
        cache.put((Long) entity.get().getId(), entity.get());
    }

}
