package com.es.phoneshop.model.checkout;

import java.math.BigDecimal;

public class DeliverySystem {
    private DeliveryMode mode;
    private DeliveryDate date;
    private BigDecimal price;
    private String address;

    public DeliverySystem(){
    }

    public DeliverySystem(DeliveryMode mode, DeliveryDate date, String address) {
        this.mode = mode;
        this.date = date;
        this.address = address;
        price = mode.getPrice();
    }

    public DeliveryMode getMode() {
        return mode;
    }

    public void setMode(DeliveryMode mode) {
        this.mode = mode;
    }

    public DeliveryDate getDate() {
        return date;
    }

    public void setDate(DeliveryDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
