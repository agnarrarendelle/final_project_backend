package practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.dto.order.OrderDto;
import practice.entity.Order;
import practice.entity.OrderItem;
import practice.entity.Product;
import practice.entity.ProductSku;
import practice.exception.ProductUnavailableException;
import practice.repository.OrderItemRepository;
import practice.repository.OrderRepository;
import practice.repository.ProductSkuRepository;
import practice.repository.ShoppingCartRepository;
import practice.service.OrderService;
import practice.utils.PageHelper;
import practice.vo.order.OrderVo;
import practice.vo.order.orderitem.OrderItemVo;
import practice.vo.shoppingcart.ShoppingCartItemVo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductSkuRepository productSkuRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public OrderVo addOrder(OrderDto orderDto, int userId) {
        List<ShoppingCartItemVo> shoppingCartItemVos = shoppingCartRepository.findAllByUserIdAndCartIdsWithProductSkuAndProduct(orderDto.getCartIds(), userId);
        List<Integer> skuIds = new ArrayList<>();
        HashMap<Integer, Integer> requiredStockNumbers = new HashMap<>();
        for (ShoppingCartItemVo shoppingCartItem : shoppingCartItemVos) {
            int skuId = shoppingCartItem.getSkuId();
            skuIds.add(skuId);

            if (!requiredStockNumbers.containsKey(skuId))
                requiredStockNumbers.put(skuId, 0);

            requiredStockNumbers.put(skuId, requiredStockNumbers.get(skuId) + shoppingCartItem.getCartNum());
        }
        Map<Integer, Integer> skuRemainingStocks = productSkuRepository.getRemainingStockNumbers(skuIds);

        if (skuRemainingStocks.isEmpty() || requiredStockNumbers.size() != skuRemainingStocks.size()) {
            throw new ProductUnavailableException("");
        }

        for (Map.Entry<Integer, Integer> skuRemainingStock : skuRemainingStocks.entrySet()) {
            int skuId = skuRemainingStock.getKey();
            int remainingStockNumber = skuRemainingStock.getValue();
            int requiredStockNumber = requiredStockNumbers.get(skuId);
            Optional<ProductSku> res = productSkuRepository.findById(skuId);

            if (res.isEmpty()) {
                throw new ProductUnavailableException("");
            }

            ProductSku sku = res.get();

            if (!requiredStockNumbers.containsKey(skuId) || requiredStockNumbers.get(skuId) > remainingStockNumber) {
                throw new ProductUnavailableException(sku.getSkuName());
            }

            sku.setStock(remainingStockNumber - requiredStockNumber);
            productSkuRepository.save(sku);

        }

        Order order = new Order();
        order.setStatus("1");
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(userId);
        order.setReceiverName(orderDto.getReceiverName());
        order.setReceiverAddress(orderDto.getReceiverAddress());
        order.setReceiverMobile(orderDto.getReceiverMobile());
        order.setOrderRemark(orderDto.getOrderRemark());
        orderRepository.save(order);

        long totalOriginalPrice = 0;
        int totalSellPrice = 0;
        List<String> allProductNames = new ArrayList<>();
        List<OrderItemVo> productNames = new ArrayList<>();
        for (ShoppingCartItemVo shoppingCartItem : shoppingCartItemVos) {
            totalOriginalPrice += (long) shoppingCartItem.getOriginalPrice() * shoppingCartItem.getCartNum();
            totalSellPrice += shoppingCartItem.getSellPrice() * shoppingCartItem.getCartNum();

            OrderItem orderItem = new OrderItem();
            allProductNames.add(shoppingCartItem.getProductName());
            orderItem.setItemId(UUID.randomUUID().toString());
            orderItem.setOrder(order);
            orderItem.setProduct(entityManager.getReference(Product.class, shoppingCartItem.getProductId()));
            orderItem.setProductName(shoppingCartItem.getProductName());
            orderItem.setProductImg(shoppingCartItem.getProductImg());
            orderItem.setSku(entityManager.getReference(ProductSku.class, shoppingCartItem.getSkuId()));
            orderItem.setSkuName(shoppingCartItem.getSkuName());
            orderItem.setProductPrice(BigDecimal.valueOf(shoppingCartItem.getSellPrice()));
            orderItem.setBuyCounts(shoppingCartItem.getCartNum());
            orderItem.setTotalAmount(BigDecimal.valueOf((long) shoppingCartItem.getSellPrice() * shoppingCartItem.getCartNum()));
            orderItem.setIsComment(0);

            orderItemRepository.save(orderItem);

            productNames.add(OrderItemVo.builder()
                    .productName(shoppingCartItem.getProductName())
                    .quantity(shoppingCartItem.getCartNum())
                    .price(BigDecimal.valueOf((long) shoppingCartItem.getSellPrice() * shoppingCartItem.getCartNum()))
                    .build());

        }

        order.setProductNames(String.join(", ", allProductNames));
        order.setTotalAmount(BigDecimal.valueOf(totalOriginalPrice));
        order.setActualAmount(totalSellPrice);
        orderRepository.save(order);


        shoppingCartRepository.deleteByCartIdIn(orderDto.getCartIds());

        return OrderVo.builder()
                .orderId(order.getOrderId())
                .orderItems(productNames)
                .build();
    }

    @Override
    @Transactional
    public void deleteExpireOrders(List<String> orderIds) {
        orderRepository.expireOrders(orderIds);
        //key: skuId
        //val: buyCounts
        Map<Integer, Integer> expiredOrderBuyCounts = orderItemRepository.findOrderItemBuyCountsInterfaceImpl(orderIds);
        List<Integer> skuIds = new ArrayList<>(expiredOrderBuyCounts.keySet());
        List<ProductSku> productSkus = productSkuRepository.findAllById(skuIds);

        for (ProductSku productSku : productSkus) {
            int buyCounts = expiredOrderBuyCounts.get(productSku.getSkuId());
            productSku.setStock(productSku.getStock() + buyCounts);
        }

        productSkuRepository.saveAll(productSkus);
    }

    @Override
    public PageHelper<OrderVo> listOrdersByUserIdAndStatus(int userId, String status, int pageNum, int limit) {
        Pageable paging = PageRequest.of(pageNum - 1, limit);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues();
        Example<Order> query = Example.of(Order.builder().userId(userId).status(status).build(), matcher);
        Page<Order> res = orderRepository.findAll(query, paging);
        List<OrderVo> vos = res.getContent().stream().map(o -> OrderVo
                .builder()
                .untitled(o.getProductNames())
                .status(o.getStatus())
                .createTime(o.getCreateTime())
                .build()
        ).toList();

        return new PageHelper<>(vos.size(), res.getTotalPages(), vos);
    }
}