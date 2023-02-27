package com.shumchenia.clevertec.service;


import com.shumchenia.clevertec.model.check.Check;
import com.shumchenia.clevertec.model.check.builder.BaseCheckBuilder;
import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import com.shumchenia.clevertec.model.product.Product;
import com.shumchenia.clevertec.repository.DiscountCardRepository;
import com.shumchenia.clevertec.repository.ProductRepository;
import com.shumchenia.clevertec.util.impl.DiscountCardTestBuilder;
import com.shumchenia.clevertec.util.impl.ProductTestBuilder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CheckServiceTest {

    @InjectMocks
    private CheckService checkService;

    @Spy
    private BaseCheckBuilder checkBuilder;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private DiscountCardRepository discountCardRepository;

    private final ProductTestBuilder productTestBuilder = new ProductTestBuilder();
    private final DiscountCardTestBuilder cardTestBuilder = new DiscountCardTestBuilder();

    @ParameterizedTest
    @ValueSource(strings = {"111"})
    void checkGetCheckReturnCheck(String code){
        DiscountCard discountCard =cardTestBuilder.build();
        Product product =productTestBuilder.build();
        int sum= (int) (product.getPrice()*((100 - discountCard.getPercent()) /100));

        doReturn(Optional.of(product)).when(productRepository).findById(product.getId());
        doReturn(Optional.of(discountCard)).when(discountCardRepository).findByCode(code);

        Check check = checkService.getCheck(List.of(product.getId().intValue()), code);

        assertThat(check.getProducts()).hasSize(1);
        assertThat(check.getDiscountCard().get()).isEqualTo(discountCard);
        assertThat(check.getSum().intValue()).isEqualTo(sum);

    }

}
