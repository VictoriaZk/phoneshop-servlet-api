package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.HttpSessionCartService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HttpSessionOrderService implements OrderService {
    private static volatile OrderService INSTANCE;

    private HttpSessionOrderService() {
    }

    public static OrderService getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpSessionOrderService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpSessionOrderService();
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public Order createOrder(Cart cart, InformationAboutClient informationAboutClient,
                             DeliverySystem deliverySystem, PaymentSystem paymentSystem) {
        Order order = new Order();
        order.setInformationAboutClient(informationAboutClient);
        order.setDeliverySystem(deliverySystem);
        order.setPaymentSystem(paymentSystem);
        order.setPriceOfProducts(cart.getTotalPrice());
        List<CartItem> cartItems = cart.getCartItems().stream()
                .map(CartItem::new)
                .collect(Collectors.toList());
        order.setCartItems(cartItems);
        return order;
    }

    @Override
    public void placeOrder(Order order, Cart cart) {
        order.setId(generateUniqueId());
        calculatePrice(order);
        ArrayListOrderDao.getInstance().save(order);
        HttpSessionCartService.getInstance().clear(cart);
    }

    @Override
    public void calculatePrice(Order order) {
        BigDecimal totalPrice = order.getPriceOfProducts().add(order.getDeliveryPrice());
        order.setTotalPrice(totalPrice);
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public PaymentSystem getPaymentSystem(String description) {
        return getPaymentSystems().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<PaymentSystem> getPaymentSystems() {
        return Arrays.asList(PaymentSystem.values());
    }

    @Override
    public DeliveryMode getDeliveryMode(String description) {
        return getDeliveryModes().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<DeliveryMode> getDeliveryModes() {
        return Arrays.asList(DeliveryMode.values());
    }

    @Override
    public DeliveryDate getDeliveryDate(String description) {
        return getDeliveryDates().stream()
                .filter(mode -> mode.getDescription().equalsIgnoreCase(description))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<DeliveryDate> getDeliveryDates() {
        return Arrays.asList(DeliveryDate.values());
    }

}
