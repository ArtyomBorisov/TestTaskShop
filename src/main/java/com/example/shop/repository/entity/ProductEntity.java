package com.example.shop.repository.entity;

import com.example.shop.enums.ProductStatus;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products", schema = "app")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Type(type = "pgsql_enum")
    private ProductStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime dtCreate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public static class Builder {
        private ProductEntity entity;

        private Builder() {
            this.entity = new ProductEntity();
        }

        public Builder setId(Integer id) {
            this.entity.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.entity.name = name;
            return this;
        }

        public Builder setPrice(Integer price) {
            this.entity.price = price;
            return this;
        }

        public Builder setStatus(ProductStatus status) {
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

        public ProductEntity build() {
            return this.entity;
        }
    }
}
