package practice.repository;

import lombok.Data;
import org.junit.jupiter.api.*;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import practice.entity.Product;
import practice.entity.ProductSku;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@Transactional
@Rollback
public class ProductSkuRepositoryTests {

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeAll
    public void beforeAll() {
        product = Product.builder()
                .productName("test product name")
                .soldNum(10)
                .productStatus(1)
                .content("test product content")
                .build();

        productRepository.save(product);
    }

    @Nested
    @DisplayName("Tests for findStockNumbers")
    @Transactional
    @Rollback
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FindStockNumbers {
        @Nested
        @DisplayName("test get one or more results")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @Transactional
        @Rollback
        class GetOneOrMoreNumbers {
            private ProductSku productSku0;
            private ProductSku productSku1;

            @BeforeEach
            public void beforeEach() {
                product = Product.builder()
                        .productName("test product name")
                        .soldNum(10)
                        .productStatus(1)
                        .content("test product content")
                        .build();

                productRepository.save(product);

                productSku0 = ProductSku.builder()
                        .productId(product.getProductId())
                        .skuName("Test Sku Name 0")
                        .skuImg("/test_image_path_0")
                        .originalPrice(100)
                        .sellPrice(50)
                        .discounts(0.5)
                        .stock(10)
                        .build();

                productSku1 = ProductSku.builder()
                        .productId(product.getProductId())
                        .skuName("Test Sku Name 1")
                        .skuImg("/test_image_path_1")
                        .originalPrice(100)
                        .sellPrice(50)
                        .discounts(0.5)
                        .stock(5)
                        .build();

                productSkuRepository.save(productSku0);
                productSkuRepository.save(productSku1);
            }

            @Test
            @DisplayName("test if the method return the expected one role")
            public void getOne() {
                List<Integer> actualStockNumbers = productSkuRepository.findStockNumbers(List.of(productSku0.getSkuId()));
                List<Integer> expectStockNumbers = List.of(10);

                assertEquals(actualStockNumbers, expectStockNumbers);
            }

            @Test
            @DisplayName("test if the method return all expected rows")
            public void getAll() {
                List<Integer> actualStockNumbers = productSkuRepository.findStockNumbers(List.of(productSku0.getSkuId(), productSku1.getSkuId()));
                List<Integer> expectStockNumbers = List.of(10, 5);

                assertEquals(actualStockNumbers, expectStockNumbers);
            }

            @Test
            @DisplayName("test if the method return all non-duplicate rows")
            public void getAllUnique() {
                List<Integer> actualStockNumbers = productSkuRepository.findStockNumbers(List.of(productSku0.getSkuId(), productSku1.getSkuId(), productSku1.getSkuId()));
                List<Integer> expectStockNumbers = List.of(10, 5);

                assertEquals(actualStockNumbers, expectStockNumbers);
            }
        }

        @Nested
        @DisplayName("test get empty results")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @Transactional
        @Rollback
        class GetEmptyResult {
            //            @BeforeAll
//            public void beforeAll(){
//                productSkuRepository.deleteAll();
//            }
            @Test
            @DisplayName("test if the method return empty set when the table is empty")
            public void getNoneWhenTableIsEmpty() {
                List<Integer> actualStockNumbers = productSkuRepository.findStockNumbers(List.of(1));
                List<Integer> expectStockNumbers = List.of();
                assertEquals(actualStockNumbers, expectStockNumbers);
                assertTrue(productSkuRepository.findAll().isEmpty());
            }

            @Test
            @DisplayName("test if the method return empty set when the table is not empty")
            public void getNoneWhenTableIsNotEmpty() {
                ProductSku productSku0 = ProductSku.builder()
                        .productId(product.getProductId())
                        .skuName("Test Sku Name 1")
                        .skuImg("/test_image_path_1")
                        .originalPrice(100)
                        .sellPrice(50)
                        .discounts(0.5)
                        .stock(5)
                        .build();

                productSkuRepository.save(productSku0);
                List<Integer> actualStockNumbers = productSkuRepository.findStockNumbers(List.of(productSku0.getSkuId() + 1));
                List<Integer> expectStockNumbers = List.of();
                assertFalse(productSkuRepository.findAll().isEmpty());
                assertEquals(actualStockNumbers, expectStockNumbers);
            }
        }
    }

    @Nested
    @DisplayName("Tests for getRemainingStockNumbers")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Transactional
    @Rollback
    class GetRemainingStockNumbersTests {

        @Nested
        @DisplayName("test get one or more results")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @Transactional
        @Rollback
        class GetOneOrMoreNumbers {
            private ProductSku productSku0;
            private ProductSku productSku1;

            @BeforeEach
            public void beforeEach() {

                productSku0 = ProductSku.builder()
                        .productId(product.getProductId())
                        .skuName("Test Sku Name 0")
                        .skuImg("/test_image_path_0")
                        .originalPrice(100)
                        .sellPrice(50)
                        .discounts(0.5)
                        .stock(10)
                        .build();

                productSku1 = ProductSku.builder()
                        .productId(product.getProductId())
                        .skuName("Test Sku Name 1")
                        .skuImg("/test_image_path_1")
                        .originalPrice(100)
                        .sellPrice(50)
                        .discounts(0.5)
                        .stock(5)
                        .build();

                productSkuRepository.save(productSku0);
                productSkuRepository.save(productSku1);
            }

            @Test
            @DisplayName("test if the method return one expected result")
            public void testGetOne() {
                Map<Integer, Integer> actualRemainingStockNumbers = productSkuRepository.getRemainingStockNumbers(List.of(this.productSku0.getSkuId()));
                Map<Integer, Integer> expectedRemainingStockNumbers = Map.ofEntries(
                        Map.entry(productSku0.getSkuId(), 10)
                );
                assertTrue(expectedRemainingStockNumbers.equals(actualRemainingStockNumbers));
            }

            @Test
            @DisplayName("test if the method return all expected results")
            public void getAll() {
                Map<Integer, Integer> actualRemainingStockNumbers = productSkuRepository.getRemainingStockNumbers(List.of(productSku0.getSkuId(), productSku1.getSkuId()));
                Map<Integer, Integer> expectedRemainingStockNumbers = Map.ofEntries(
                        Map.entry(productSku0.getSkuId(), 10),
                        Map.entry(productSku1.getSkuId(), 5)
                );
                assertTrue(expectedRemainingStockNumbers.equals(actualRemainingStockNumbers));
            }

            @Test
            @DisplayName("test if the method return all unique results")
            public void getAllUnique() {
                Map<Integer, Integer> actualRemainingStockNumbers = productSkuRepository.getRemainingStockNumbers(List.of(productSku0.getSkuId(), productSku1.getSkuId(), productSku1.getSkuId()));
                Map<Integer, Integer> expectedRemainingStockNumbers = Map.ofEntries(
                        Map.entry(productSku0.getSkuId(), 10),
                        Map.entry(productSku1.getSkuId(), 5)
                );
                assertTrue(expectedRemainingStockNumbers.equals(actualRemainingStockNumbers));
            }
        }

        @Nested
        @DisplayName("test zero results")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        @Transactional
        @Rollback
        public class GetNoneResult {

            @Test
            @DisplayName("test if the method empty set when the table is empty")
            public void getNoneWhenTableIsEmpty() {
                Map<Integer, Integer> remainingStockNumbers = productSkuRepository.getRemainingStockNumbers(List.of(1));

                assertTrue(remainingStockNumbers.isEmpty());
                assertTrue(productSkuRepository.findAll().isEmpty());
            }

            @Test
            @DisplayName("test if the method empty set when the table is not empty")
            public void getNoneWhenTableIsNotEmpty() {
                ProductSku productSku0 = ProductSku.builder()
                        .productId(product.getProductId())
                        .skuName("Test Sku Name 1")
                        .skuImg("/test_image_path_1")
                        .originalPrice(100)
                        .sellPrice(50)
                        .discounts(0.5)
                        .stock(5)
                        .build();

                productSkuRepository.save(productSku0);
                Map<Integer, Integer> remainingStockNumbers = productSkuRepository.getRemainingStockNumbers(List.of(productSku0.getSkuId() + 1));

                assertTrue(remainingStockNumbers.isEmpty());
            }
        }
    }
}


