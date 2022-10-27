package com.example.shop.dto;

public class ProductResponseDTO {
    private String name;
    private int price;
    private int amountOrders;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmountOrders() {
        return amountOrders;
    }

    public static class Builder {
        private ProductResponseDTO dto;

        private Builder() {
            this.dto = new ProductResponseDTO();
        }

        public Builder setName(String name) {
            this.dto.name = name;
            return this;
        }

        public Builder setPrice(int price) {
            this.dto.price = price;
            return this;
        }

        public Builder setAmountOrders(int amountOrders) {
            this.dto.amountOrders = amountOrders;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public ProductResponseDTO build() {
            return this.dto;
        }
    }
}
