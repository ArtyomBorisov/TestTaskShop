package com.example.shop.repository.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders", schema = "app")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime dtCreate;

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public static class Builder {
        private OrderEntity entity;

        private Builder() {
            this.entity = new OrderEntity();
        }

        public Builder setId(Integer id) {
            this.entity.id = id;
            return this;
        }

        public Builder setUserId(Integer userId) {
            this.entity.userId = userId;
            return this;
        }

        public Builder setStatus(String status) {
            this.entity.status = status;
            return this;
        }

        public Builder setDtCreate(LocalDateTime dtCreate) {
            this.entity.dtCreate = dtCreate;
            return this;
        }

        public static Builder createBuilder() {
            return new Builder();
        }

        public OrderEntity build() {
            return this.entity;
        }
    }
}
