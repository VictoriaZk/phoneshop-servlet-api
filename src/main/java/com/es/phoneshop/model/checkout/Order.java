package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<CartItem> cartItems = new ArrayList<>();
    private InformationAboutClient informationAboutClient;
    private DeliverySystem deliverySystem;
    private PaymentSystem paymentSystem;
    private BigDecimal priceOfProducts = BigDecimal.ZERO;
    private BigDecimal deliveryPrice = BigDecimal.TEN;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getPriceOfProducts() {
        return priceOfProducts;
    }

    public void setPriceOfProducts(BigDecimal priceOfProducts) {
        this.priceOfProducts = priceOfProducts;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public InformationAboutClient getInformationAboutClient() {
        return informationAboutClient;
    }

    public void setInformationAboutClient(InformationAboutClient informationAboutClient) {
        this.informationAboutClient = informationAboutClient;
    }

    public DeliverySystem getDeliverySystem() {
        return deliverySystem;
    }

    public void setDeliverySystem(DeliverySystem deliverySystem) {
        this.deliverySystem = deliverySystem;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}
