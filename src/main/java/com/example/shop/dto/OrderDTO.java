package com.example.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class OrderDTO {
    @JsonProperty("user_id")
    private int userId;
    private String status;
    private Map<String, Integer> products;

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public static class Builder {
        private OrderDTO orderDTO;

        private Builder() {
            this.orderDTO = new OrderDTO();
        }

        public Builder setUserId(int userId) {
            this.orderDTO.userId = userId;
            return this;
        }

        public Builder setStatus(String status) {
            this.orderDTO.status = status;
            return this;
        }

        public Builder setQuantityProducts(Map<String, Integer> quantityProducts) {
            this.orderDTO.products = quantityProducts;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public OrderDTO build() {
            return this.orderDTO;
        }
    }
}
