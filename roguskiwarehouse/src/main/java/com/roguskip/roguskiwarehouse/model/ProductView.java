package com.roguskip.roguskiwarehouse.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductView {

    private String manufacturerName;
    private String productName;
    private Long manufacturerId;
    private BigDecimal price;
    private Integer quantity;
    private Long productId;
    private String currency;
    private String productGUID;
    private String color;
    private Long warehouseId;
}
