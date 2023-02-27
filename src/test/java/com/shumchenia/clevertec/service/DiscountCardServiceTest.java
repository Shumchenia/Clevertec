package com.shumchenia.clevertec.service;


import com.shumchenia.clevertec.dto.discountCard.DiscountCardReadDto;
import com.shumchenia.clevertec.dto.product.ProductReadDto;
import com.shumchenia.clevertec.mapper.discountCard.DiscountCardCreateEditMapper;
import com.shumchenia.clevertec.mapper.discountCard.DiscountCardReadMapper;
import com.shumchenia.clevertec.mapper.product.ProductCreateEditMapper;
import com.shumchenia.clevertec.mapper.product.ProductReadMapper;
import com.shumchenia.clevertec.model.discountCard.DiscountCard;
import com.shumchenia.clevertec.model.product.Product;
import com.shumchenia.clevertec.repository.DiscountCardRepository;
import com.shumchenia.clevertec.repository.ProductRepository;
import com.shumchenia.clevertec.util.impl.DiscountCardTestBuilder;
import com.shumchenia.clevertec.util.impl.ProductTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DiscountCardServiceTest {

    @InjectMocks
    private DiscountCardService service;

    @Mock
    private DiscountCardRepository repository;

    @Spy
    private DiscountCardReadMapper readMapper;

    @Spy
    private DiscountCardCreateEditMapper createEditMapper;

    private final DiscountCardTestBuilder testBuilder = new DiscountCardTestBuilder();

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("test should return list of 1 element")
        void checkFindAllReturnListOfSizeOne() {
            DiscountCard discountCard = testBuilder.build();

            when(repository.findAll()).thenReturn(List.of(discountCard));

            assertThat(service.findAll()).hasSize(1);
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("test should return empty list")
        void checkFindAllReturnEmptuList() {
            List<DiscountCard> cards = List.of();

            when(repository.findAll()).thenReturn(cards);

            assertThat(service.findAll()).hasSize(0);
            verify(repository, times(1)).findAll();
        }

    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("test should return DiscountCardReadDto")
        void checkFindByIdReturnDiscountCardReadDto() {
            DiscountCard discountCard = testBuilder.build();
            Long id = discountCard.getId();
            DiscountCardReadDto expected = readMapper.map(discountCard);

            doReturn(Optional.of(discountCard))
                    .when(repository)
                    .findById(id);

            DiscountCardReadDto actual = service.findById(id);

            assertThat(actual).isEqualTo(expected);
            verify(repository, times(1)).findById(id);
        }


    }

    @Nested
    class CreateTest {
        @Test
        @DisplayName("test should return DiscountCardReadDto")
        void checkCreateReturnDiscountCardReadDto() {
            DiscountCard expected = testBuilder.build();

            when(repository.save(any(DiscountCard.class))).thenReturn(expected);

            DiscountCardReadDto actual = service.create(createEditMapper.map(expected));

            assertThat(actual).usingRecursiveComparison().isEqualTo(readMapper.map(expected));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        @DisplayName("test should update and return DiscountCardReadDto")
        void checkUpdateReturnDiscountCardReadDto() {
            DiscountCard expected = testBuilder.build();

            when(repository.findById(anyLong())).thenReturn(Optional.of(expected));
            when(repository.saveAndFlush(any(DiscountCard.class))).thenReturn(expected);

            DiscountCardReadDto actual = service.update(expected.getId(), createEditMapper.map(expected));

            assertThat(actual).usingRecursiveComparison().isEqualTo(readMapper.map(expected));
        }
    }

    @Nested
    class DeleteTest {

        @Test
        void checkDeleteOneDiscountCard() {
            DiscountCard expected = testBuilder.build();
            long id = expected.getId();

            doReturn(Optional.of(expected))
                    .when(repository)
                    .findById(id);

            doNothing()
                    .when(repository)
                    .deleteById(id);

            service.delete(id);

            verify(repository, times(1))
                    .deleteById(id);
        }
    }

}
