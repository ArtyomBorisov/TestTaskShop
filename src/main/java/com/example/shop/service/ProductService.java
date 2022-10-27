package com.example.shop.service;

import com.example.shop.dto.ProductRequestDTO;
import com.example.shop.dto.ProductResponseDTO;
import com.example.shop.repository.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    int create(ProductRequestDTO product);
    void update(String oldName, ProductRequestDTO product);
    void delete(String name);
    List<ProductResponseDTO> get();
    boolean isProductExist(String name);
    ProductEntity get(String name);
}
