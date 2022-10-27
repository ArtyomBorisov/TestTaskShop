package com.example.shop.repository.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_items", schema = "app")
public class OrderItemsEntity {

    @EmbeddedId
    private OrderItemsPK pk;

    private Integer quantity;

    public OrderItemsEntity() {
    }

    public OrderItemsEntity(OrderItemsPK pk, Integer quantity) {
        this.pk = pk;
        this.quantity = quantity;
    }

    public OrderItemsPK getPk() {
        return pk;
    }

    public void setPk(OrderItemsPK pk) {
        this.pk = pk;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Embeddable
    public static class OrderItemsPK implements Serializable {

        @ManyToOne
        @JoinColumn(name = "order_id")
        private OrderEntity orderEntity;

        @ManyToOne
        @JoinColumn(name = "product_id")
        private ProductEntity productEntity;

        public OrderItemsPK() {
        }

        public OrderItemsPK(OrderEntity orderEntity, ProductEntity productEntity) {
            this.orderEntity = orderEntity;
            this.productEntity = productEntity;
        }

        public OrderEntity getOrderEntity() {
            return orderEntity;
        }

        public ProductEntity getProductEntity() {
            return productEntity;
        }
    }
}
