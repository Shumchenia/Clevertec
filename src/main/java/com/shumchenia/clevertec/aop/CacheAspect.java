package com.shumchenia.clevertec.aop;

import com.shumchenia.clevertec.cache.Cache;
import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CacheAspect {

    @Value("${cache.algorithm}")
    public String algorithm;

    private Cache<Long, DiscountCard> cache;

    private final BeanFactory beanFactory;

    @PostConstruct
    public void setAlgorithm() {
        this.cache = beanFactory.getBean(algorithm, Cache.class);
    }

    @Around("com.shumchenia.clevertec.aop.Pointcuts.isFindById()" +
            "&& args(id)")
    public Object cachingFindById(ProceedingJoinPoint point, Object id) throws Throwable {
        if (cache.get((Long) id).isPresent()) {
            return cache.get((Long) id);
        }
        Optional<DiscountCard> obj = (Optional<DiscountCard>) point.proceed();
        obj.ifPresent(card -> cache.put((Long) id, card));
        return obj;
    }
    @Around("com.shumchenia.clevertec.aop.Pointcuts.isFindAll()")
    public Object cachingFindAll(ProceedingJoinPoint point) throws Throwable {
        List<DiscountCard> list = (List<DiscountCard>) point.proceed();
        list.stream().forEach(x->cache.put(x.getId(),x));
        return list;
    }


    @Around("com.shumchenia.clevertec.aop.Pointcuts.isDeleteById()" +
            "&& args(id)")
    public void cachingDeleteById(ProceedingJoinPoint point, Object id) throws Throwable {
        if (cache.get((Long) id).isPresent()) {
            cache.remove((Long) id);
        }
        point.proceed();
    }

    @Around("com.shumchenia.clevertec.aop.Pointcuts.isSave()")
    public void cachingSave(ProceedingJoinPoint point) throws Throwable {
        Optional<DiscountCard> card = (Optional<DiscountCard>) point.proceed();
        cache.put(card.get().getId(), card.get());
    }

}
