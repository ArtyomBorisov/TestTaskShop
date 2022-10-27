package com.example.shop.service.impl;

import com.example.shop.dto.ProductRequestDTO;
import com.example.shop.dto.ProductResponseDTO;
import com.example.shop.enums.ProductStatus;
import com.example.shop.exception.ValidationException;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.entity.ProductEntity;
import com.example.shop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final Random random;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
        this.random = new Random();
    }

    @Transactional
    @Override
    public int create(ProductRequestDTO product) {
        isProductNull(product);
        isNameUnique(product);
        checkPrice(product);

        ProductStatus[] statuses = ProductStatus.values();
        int statusNum = random.nextInt(statuses.length);

        ProductEntity entity = ProductEntity.Builder.createBuilder()
                .setName(product.getName())
                .setPrice(product.getPrice())
                .setStatus(statuses[statusNum])
                .setDtCreate(LocalDateTime.now())
                .build();

        return repository.save(entity).getId();
    }

    @Transactional
    @Override
    public void update(String oldName, ProductRequestDTO product) {
        isProductNull(product);
        isStatusNull(product);
        checkPrice(product);

        String newName = product.getName();
        ProductEntity entity = get(oldName);

        if (!oldName.equals(newName)) {
            isNameUnique(product);
            entity.setName(newName);
        }

        entity.setPrice(product.getPrice());
        entity.setStatus(product.getStatus());
    }

    @Transactional
    @Override
    public void delete(String name) {
        ProductEntity entity = get(name);
        ProductStatus status = entity.getStatus();

        if (status == ProductStatus.in_stock || status == ProductStatus.running_low) {
            throw new ValidationException("Нельзя удалить продукт в статусах IN_STOCK и RUNNING_LOW");
        }

        repository.delete(entity);
    }

    @Override
    public List<ProductResponseDTO> get() {
        List<ProductEntity> entities = repository.getSortedByOrdersNumbers();

        List<ProductResponseDTO> result = new ArrayList<>();

        for (ProductEntity entity : entities) {
            String nameProduct = entity.getName();

            Integer ordersNumbers = repository.getOrdersNumbers(nameProduct);

            ProductResponseDTO dto = ProductResponseDTO.Builder.createBuilder()
                    .setName(nameProduct)
                    .setPrice(entity.getPrice())
                    .setAmountOrders(ordersNumbers)
                    .build();

            result.add(dto);
        }

        return result;
    }

    @Override
    public boolean isProductExist(String name) {
        Optional<ProductEntity> productEntity = repository.findByName(name);

        return productEntity.isPresent() && !productEntity.get().getStatus().equals(ProductStatus.out_of_stock);
    }

    @Override
    public ProductEntity get(String name) {
        return repository.findByName(name).orElseThrow(
                () -> new ValidationException("Передано название несуществующего продукта"));
    }

    private void isProductNull(ProductRequestDTO dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException("Не передан продукт");
        }
    }

    private void isNameUnique(ProductRequestDTO dto) throws ValidationException {
        if (dto.getName() == null) {
            throw new ValidationException("Не передано название продукта");
        }

        if (repository.findByName(dto.getName()).isPresent()) {
            throw new ValidationException("Передано не уникальное название продукта");
        }
    }

    private void checkPrice(ProductRequestDTO dto) throws ValidationException {
        if (dto.getPrice() == 0) {
            throw new ValidationException("Не передана цена продукта");
        } else if (dto.getPrice() < 0) {
            throw new ValidationException("Передана отрицательная цена продукта");
        }
    }

    private void isStatusNull(ProductRequestDTO dto) throws ValidationException {
        if (dto.getStatus() == null) {
            throw new ValidationException("Не передан статус продукта");
        }
    }
}
