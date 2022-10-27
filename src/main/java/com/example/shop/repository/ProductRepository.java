package com.example.shop.repository;

import com.example.shop.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findByName(String name);

    @Query(value = "SELECT id, name, price, status, created_at\n" +
            "FROM app.products\n" +
            "LEFT JOIN app.order_items ON app.products.id = app.order_items.product_id\n" +
            "GROUP BY app.products.id\n" +
            "ORDER BY COUNT(app.products.id) DESC", nativeQuery = true)
    List<ProductEntity> getSortedByOrdersNumbers();

    @Query(value = "SELECT COUNT(product_id) \n" +
            "FROM app.products\n" +
            "LEFT JOIN app.order_items ON app.products.id = app.order_items.product_id\n" +
            "WHERE products.name = ?1\n" +
            "GROUP BY app.products.id\n" +
            "ORDER BY 1 DESC", nativeQuery = true)
    Integer getOrdersNumbers(String name);
}
