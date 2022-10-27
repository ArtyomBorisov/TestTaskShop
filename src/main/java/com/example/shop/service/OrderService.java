package com.example.shop.service;

import com.example.shop.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    int create(OrderDTO order);
    List<OrderDTO> get();
}
