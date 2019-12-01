package com.es.phoneshop.model.checkout;

import java.math.BigDecimal;

public enum DeliveryMode {
    STORE_PICKUP(BigDecimal.ZERO, "Store pickup"), COURIER(BigDecimal.TEN, "Courier");

    private BigDecimal price;
    private String description;

    DeliveryMode(BigDecimal price, String description) {
        this.price = price;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
