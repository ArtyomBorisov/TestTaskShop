package com.example.shop.repository;

import com.example.shop.repository.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItemsEntity, OrderItemsEntity.OrderItemsPK> {
    List<OrderItemsEntity> findAllByPkOrderEntityId(Integer id);
    Optional<OrderItemsEntity> findByPkProductEntityName(String name);
}
