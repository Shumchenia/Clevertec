package com.shumchenia.clevertec.service;

import com.shumchenia.clevertec.dto.product.ProductReadDto;
import com.shumchenia.clevertec.mapper.product.ProductCreateEditMapper;
import com.shumchenia.clevertec.mapper.product.ProductReadMapper;
import com.shumchenia.clevertec.model.product.Product;
import com.shumchenia.clevertec.repository.ProductRepository;
import com.shumchenia.clevertec.util.impl.ProductTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Spy
    private ProductReadMapper readMapper;

    @Spy
    private ProductCreateEditMapper createEditMapper;

    private final ProductTestBuilder testBuilder = new ProductTestBuilder();

    @Nested
    class FindAllTest {

        @Test
        @DisplayName("test should return list of 1 element")
        void checkFindAllReturnListOfSizeOne() {
            Product product = testBuilder.build();

            when(repository.findAll()).thenReturn(List.of(product));

            assertThat(service.findAll()).hasSize(1);
            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("test should return empty list")
        void checkFindAllReturnEmptuList() {
            List<Product> products = List.of();

            when(repository.findAll()).thenReturn(products);

            assertThat(service.findAll()).hasSize(0);
            verify(repository, times(1)).findAll();
        }

    }

    @Nested
    class FindByIdTest {

        @Test
        @DisplayName("test should return productReadDto")
        void checkFindByIdReturnProductReadDto() {
            Product product = testBuilder.build();
            Long id = product.getId();
            ProductReadDto expected = readMapper.map(product);

            doReturn(Optional.of(product))
                    .when(repository)
                    .findById(id);

            ProductReadDto actual = service.findById(id);

            assertThat(actual).isEqualTo(expected);
            verify(repository, times(1)).findById(id);
        }


    }

    @Nested
    class CreateTest {
        @Test
        @DisplayName("test should return ProductReadDto")
        void checkCreateReturnProductReadDto() {
            Product expected = testBuilder.build();

            when(repository.save(any(Product.class))).thenReturn(expected);

            ProductReadDto actual = service.create(createEditMapper.map(expected));

            assertThat(actual).usingRecursiveComparison().isEqualTo(readMapper.map(expected));
        }
    }

    @Nested
    class UpdateTest {
        @Test
        @DisplayName("test should update and return ProductReadDto")
        void checkUpdateReturnProductReadDto() {
            Product expected = testBuilder.build();

            when(repository.findById(anyLong())).thenReturn(Optional.of(expected));
            when(repository.saveAndFlush(any(Product.class))).thenReturn(expected);

            ProductReadDto actual = service.update(expected.getId(), createEditMapper.map(expected));

            assertThat(actual).usingRecursiveComparison().isEqualTo(readMapper.map(expected));
        }
    }

    @Nested
    class DeleteTest {

        @Test
        void checkDeleteOneProduct() {
            Product expected = testBuilder.build();
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