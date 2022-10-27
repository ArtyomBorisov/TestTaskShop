package com.example.shop.service.impl;

import com.example.shop.dto.OrderDTO;
import com.example.shop.exception.ValidationException;
import com.example.shop.repository.OrderItemsRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.entity.OrderEntity;
import com.example.shop.repository.entity.OrderItemsEntity;
import com.example.shop.repository.entity.ProductEntity;
import com.example.shop.service.OrderService;
import com.example.shop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemsRepository orderItemsRepository,
                            ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.productService = productService;
    }

    @Transactional
    @Override
    public int create(OrderDTO order) {
        checkOrder(order);

        OrderEntity orderForSave = OrderEntity.Builder.createBuilder()
                .setUserId(order.getUserId())
                .setStatus(order.getStatus())
                .setDtCreate(LocalDateTime.now())
                .build();

        OrderEntity savedOrder = orderRepository.save(orderForSave);
        Integer idOrder = savedOrder.getId();

        Map<String, Integer> products = order.getProducts();

        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            ProductEntity productEntity = productService.get(entry.getKey());

            OrderItemsEntity.OrderItemsPK pk = new OrderItemsEntity.OrderItemsPK(savedOrder, productEntity);
            OrderItemsEntity orderItemsEntity = new OrderItemsEntity(pk, entry.getValue());

            orderItemsRepository.save(orderItemsEntity);
        }

        return idOrder;
    }

    @Override
    public List<OrderDTO> get() {
        List<OrderDTO> result = new ArrayList<>();
        List<OrderEntity> entities = orderRepository.findAllByOrderByUserIdAscDtCreateDesc();

        for (OrderEntity entity : entities) {
            Integer idOrder = entity.getId();
            List<OrderItemsEntity> listOfProducts = orderItemsRepository.findAllByPkOrderEntityId(idOrder);

            Map<String, Integer> products = new HashMap<>();
            for (OrderItemsEntity orderItemsEntity : listOfProducts) {
                String name = orderItemsEntity.getPk().getProductEntity().getName();
                Integer quantity = orderItemsEntity.getQuantity();

                products.put(name, quantity);
            }

            OrderDTO dto = OrderDTO.Builder.createBuilder()
                    .setUserId(entity.getUserId())
                    .setStatus(entity.getStatus())
                    .setQuantityProducts(products)
                    .build();

            result.add(dto);
        }

        return result;
    }

    private void checkOrder(OrderDTO order) throws ValidationException {
        if (order == null) {
            throw new ValidationException("Не передан заказ");
        }

        if (order.getUserId() < 1) {
            throw new ValidationException("Не передан id пользователя / Передан неверный");
        }

        Map<String, Integer> products = order.getProducts();

        if (products == null || products.isEmpty()) {
            throw new ValidationException("Не передан список продуктов / Передан пустой");
        }

        for (String productName : products.keySet()) {
            if (!productService.isProductExist(productName)) {
                throw new ValidationException("Передан название несуществующего (отсутствующего в наличии) продукта: "
                        + productName);
            }
        }


    }
}
