package com.example.shop.controller.rest;

import com.example.shop.dto.ProductRequestDTO;
import com.example.shop.dto.ProductResponseDTO;
import com.example.shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponseDTO> get() {
        return productService.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int create(@RequestBody ProductRequestDTO dto) {
        return productService.create(dto);
    }

    @PutMapping(value = "/{oldName}")
    public void update(@PathVariable String oldName,
                       @RequestBody ProductRequestDTO dto) {
        productService.update(oldName, dto);
    }

    @DeleteMapping(value = "/{name}")
    public void delete(@PathVariable String name) {
        productService.delete(name);
    }
}
