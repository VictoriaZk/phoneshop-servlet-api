package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;
import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart, InformationAboutClient informationAboutClient,
                      DeliverySystem deliverySystem, PaymentSystem paymentSystem);
    void placeOrder(Order order, Cart cart);
    void calculatePrice(Order order);
    PaymentSystem getPaymentSystem(String description);
    List<PaymentSystem> getPaymentSystems();
    DeliveryMode getDeliveryMode(String description);
    List<DeliveryMode> getDeliveryModes();
    DeliveryDate getDeliveryDate(String description);
    List<DeliveryDate> getDeliveryDates();
}
