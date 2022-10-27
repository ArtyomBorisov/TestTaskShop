package com.example.shop.dto;

import com.example.shop.enums.ProductStatus;

public class ProductRequestDTO {
    private String name;
    private int price;
    private ProductStatus status;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, int price, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public static class Builder {
        private ProductRequestDTO dto;

        private Builder() {
            this.dto = new ProductRequestDTO();
        }

        public Builder setName(String name) {
            this.dto.name = name;
            return this;
        }

        public Builder setPrice(int price) {
            this.dto.price = price;
            return this;
        }

        public Builder setStatus(ProductStatus status) {
            this.dto.status = status;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public ProductRequestDTO build() {
            return this.dto;
        }
    }
}
