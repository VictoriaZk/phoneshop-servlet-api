package com.es.phoneshop.model.checkout;

public enum PaymentSystem {
    CARD("Card"), CASH("Cash");
    private String description;

    PaymentSystem(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
