package com.example.shop.controller.rest;

import com.example.shop.dto.OrderDTO;
import com.example.shop.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public int create(@RequestBody OrderDTO dto) {
        return orderService.create(dto);
    }

    @GetMapping
    public List<OrderDTO> get() {
        return orderService.get();
    }
}
