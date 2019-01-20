package com.roguskip.roguskiwarehouse.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Data
public class ProductAddView {
    Long id;
    private String name;
    private Currency currency;
    private BigDecimal price;
    private Integer quantity;
    private String color;
    private Long warehouseId;
    private UUID uuid;
}