package practice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.dto.order.OrderDto;
import practice.entity.ProductSku;
import practice.exception.ProductUnavailableException;
import practice.repository.OrderItemRepository;
import practice.repository.OrderRepository;
import practice.repository.ProductSkuRepository;
import practice.repository.ShoppingCartRepository;
import practice.service.impl.OrderServiceImpl;
import practice.vo.order.OrderVo;
import practice.vo.order.orderitem.OrderItemVo;
import practice.vo.shoppingcart.ShoppingCartItemVo;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTests {

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Mock
    ProductSkuRepository productSkuRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    Faker faker = new Faker();


    @Nested
    @DisplayName("Tests cases for addOrder")
    class AddOrder {
        @Nested
        @DisplayName("Checks for exception")
        public class TestAddOrderException {
            @Test
            @DisplayName("Tests if the exception is thrown correctly when there is no any product available")
            public void addOrder_NoProductAvailable() {
                when(shoppingCartRepository.findAllByUserIdAndCartIdsWithProductSkuAndProduct(anyList(), anyInt())).thenAnswer(i->{
                    Object[] args = i.getArguments();
                    List<Integer> cartIds = (List<Integer>) args[0];

                    return cartIds.stream().map(cid->getRandomShoppingCartItemVo(1,1,cid,1)).collect(Collectors.toList());
                });
                when(productSkuRepository.getRemainingStockNumbers(anyList())).thenReturn(Collections.emptyMap());

                OrderDto orderDto = OrderDto.builder()
                        .orderRemark("test order remark")
                        .receiverAddress("test receive address")
                        .receiverMobile("test receive mobile")
                        .receiverName("test receive name")
                        .cartIds(List.of(1,2,3))
                        .build();

                Exception e = assertThrows(ProductUnavailableException.class, () -> {
                    orderService.addOrder(orderDto, 1);
                });

                assertTrue(e.getMessage().contains("Is currently unavailable"));
            }

            @Test
            @DisplayName("Tests if the exception is thrown correctly when there are some products unavailable")
            public void addOrder_SomeProductUnAvailable() {
                when(shoppingCartRepository.findAllByUserIdAndCartIdsWithProductSkuAndProduct(anyList(), anyInt())).thenReturn(List.of(getRandomShoppingCartItemVo(1,1,1,1), getRandomShoppingCartItemVo(1,2,2,2)));
                when(productSkuRepository.getRemainingStockNumbers(anyList())).thenReturn(Collections.singletonMap(1,2));

                OrderDto orderDto = OrderDto.builder()
                        .orderRemark("test order remark")
                        .receiverAddress("test receive address")
                        .receiverMobile("test receive mobile")
                        .receiverName("test receive name")
                        .cartIds(List.of(1, 2))
                        .build();

                Exception e = assertThrows(ProductUnavailableException.class, () -> {
                    orderService.addOrder(orderDto, 1);
                });

                assertTrue(e.getMessage().contains("Is currently unavailable"));
            }

            @Test
            @DisplayName("Tests if the exception is thrown correctly when the required product numbers exceed the available ones")
            public void addOrder_ProductStockNumbersTooSmall() {
                when(shoppingCartRepository.findAllByUserIdAndCartIdsWithProductSkuAndProduct(anyList(), anyInt())).thenReturn(List.of(getRandomShoppingCartItemVo(1,1,1,100), getRandomShoppingCartItemVo(1,2,2,200)));
                when(productSkuRepository.getRemainingStockNumbers(anyList())).thenAnswer(i-> {
                    Object[] args = i.getArguments();
                    List<Integer> skuIds =(List<Integer>) args[0];
                    return skuIds.stream().collect(Collectors.toMap(Integer::intValue, x -> 2));
                });
                when(productSkuRepository.findById(anyInt())).thenReturn(Optional.of(ProductSku.builder().build()));


                OrderDto orderDto = OrderDto.builder()
                        .orderRemark("test order remark")
                        .receiverAddress("test receive address")
                        .receiverMobile("test receive mobile")
                        .receiverName("test receive name")
                        .cartIds(List.of(1, 2))
                        .build();

                Exception e = assertThrows(ProductUnavailableException.class, () -> {
                    orderService.addOrder(orderDto, 1);
                });

                assertTrue(e.getMessage().contains("Is currently unavailable"));
            }
        }

        @Nested
        @DisplayName("Success")
        public class TestAddOrderSuccess{

            private static String orderId;

            @BeforeAll
            public static void beforeAll(){
                UUID id = UUID.fromString("82326cbc-6cc0-11ee-b962-0242ac120002");
                orderId = id.toString();
                mockStatic(UUID.class);
                when(UUID.randomUUID()).thenReturn(id);

            }

            @Test
            @DisplayName("1 order and 1 sku")
            public void addOrder_SingleOrderSingleSku(){
                String productName = "test product name";
                int price = 10;
                ShoppingCartItemVo shoppingCartItemVo = getRandomShoppingCartItemVo(1,1,1,1);
                shoppingCartItemVo.setProductName(productName);
                shoppingCartItemVo.setSellPrice(price);
                when(shoppingCartRepository.findAllByUserIdAndCartIdsWithProductSkuAndProduct(anyList(), anyInt())).thenReturn(List.of(shoppingCartItemVo));
                when(productSkuRepository.getRemainingStockNumbers(anyList())).thenReturn(Collections.singletonMap(1,10));
                when(productSkuRepository.findById(anyInt())).thenReturn(Optional.of(ProductSku.builder().skuId(1).skuName("test sku name").stock(10).build()));

                OrderDto orderDto = OrderDto.builder()
                        .orderRemark("test order remark")
                        .receiverAddress("test receive address")
                        .receiverMobile("test receive mobile")
                        .receiverName("test receive name")
                        .cartIds(List.of(1))
                        .build();
                OrderVo expected = OrderVo.builder().orderId(orderId).orderItems(List.of(OrderItemVo.builder().productName(productName).quantity(1).price(BigDecimal.valueOf(10)).build())).build();
                OrderVo actual = orderService.addOrder(orderDto, 1);
                assertEquals(expected, actual);

            }

            @Test
            @DisplayName("Many order and 1 sku")
            public void addOrder_ManyOrderSingleSku(){
                String productName1 = "test product name 1";
                int price = 10;
                ShoppingCartItemVo shoppingCartItemVo1 = getRandomShoppingCartItemVo(1,1,1,1);
                String productName2 = "test product name 2";
                ShoppingCartItemVo shoppingCartItemVo2 = getRandomShoppingCartItemVo(2,1,2,2);

                shoppingCartItemVo1.setProductName(productName1);
                shoppingCartItemVo2.setProductName(productName2);
                shoppingCartItemVo1.setSellPrice(price);
                shoppingCartItemVo2.setSellPrice(price);
                when(shoppingCartRepository.findAllByUserIdAndCartIdsWithProductSkuAndProduct(anyList(), anyInt())).thenReturn(List.of(shoppingCartItemVo1, shoppingCartItemVo2));
                when(productSkuRepository.getRemainingStockNumbers(anyList())).thenReturn(Collections.singletonMap(1,10));
                when(productSkuRepository.findById(anyInt())).thenReturn(Optional.of(ProductSku.builder().skuId(1).skuName("test sku name").stock(10).build()));

                OrderDto orderDto = OrderDto.builder()
                        .orderRemark("test order remark")
                        .receiverAddress("test receive address")
                        .receiverMobile("test receive mobile")
                        .receiverName("test receive name")
                        .cartIds(List.of(1, 2))
                        .build();
                OrderVo expected = OrderVo.builder()
                        .orderId(orderId)
                        .orderItems(List.of(
                                OrderItemVo.builder().productName(productName1).quantity(1).price(BigDecimal.valueOf(10)).build(),
                                OrderItemVo.builder().productName(productName2).quantity(2).price(BigDecimal.valueOf(20)).build()
                                )
                            )
                        .build();
                OrderVo actual = orderService.addOrder(orderDto, 1);

                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Many orders and Many skus")
            public void addOrder_ManyOrdersManySkus(){
                String productName1 = "test product name 1";
                ShoppingCartItemVo shoppingCartItemVo1 = getRandomShoppingCartItemVo(1,1,1,1);
                String productName2 = "test product name 2";
                ShoppingCartItemVo shoppingCartItemVo2 = getRandomShoppingCartItemVo(2,2,2,2);
                int price = 10;

                shoppingCartItemVo1.setProductName(productName1);
                shoppingCartItemVo2.setProductName(productName2);
                shoppingCartItemVo1.setSellPrice(price);
                shoppingCartItemVo2.setSellPrice(price);
                when(shoppingCartRepository.findAllByUserIdAndCartIdsWithProductSkuAndProduct(anyList(), anyInt())).thenReturn(List.of(shoppingCartItemVo1, shoppingCartItemVo2));
                when(productSkuRepository.getRemainingStockNumbers(anyList())).thenReturn(Map.of(1, 3, 2, 3));
                when(productSkuRepository.findById(anyInt())).thenAnswer(i-> {
                    Object[] args = i.getArguments();
                    int skuId = (int) args[0];
                    return Optional.of(ProductSku.builder().skuId(skuId).skuName("test sku name").stock(10).build());
                });

                OrderDto orderDto = OrderDto.builder()
                        .orderRemark("test order remark")
                        .receiverAddress("test receive address")
                        .receiverMobile("test receive mobile")
                        .receiverName("test receive name")
                        .cartIds(List.of(1, 2))
                        .build();
                OrderVo expected = OrderVo.builder().orderId(orderId)
                        .orderItems(List.of(
                                OrderItemVo.builder().productName(productName1).quantity(1).price(BigDecimal.valueOf(10)).build(),
                                OrderItemVo.builder().productName(productName2).quantity(2).price(BigDecimal.valueOf(20)).build()
                            )
                        )
                        .build();
                OrderVo actual = orderService.addOrder(orderDto, 1);
                assertEquals(expected, actual);
            }
        }

    }

    private ShoppingCartItemVo getRandomShoppingCartItemVo(int productId, int skuId, int cartId, int cartNum){
        String productName = faker.commerce().productName();
        Integer originalPrice = (int) Float.parseFloat(faker.commerce().price());
        return ShoppingCartItemVo.builder()
                .productName(productName)
                .productImg(String.format("%s.img", productName))
                .productId(productId)
                .skuName(String.format("%s_sku", productName))
                .skuProps(faker.commerce().color())
                .skuId(skuId)
                .originalPrice(originalPrice)
                .sellPrice((int) (originalPrice*0.5))
                .cartNum(cartNum)
                .cartId(cartId)
                .build();
    }

}
