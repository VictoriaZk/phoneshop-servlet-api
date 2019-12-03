package com.es.phoneshop.model.checkout;

public enum DeliveryDate {
    TODAY("Today"), TOMORROW("Tomorrow");
    private String description;

    DeliveryDate(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
