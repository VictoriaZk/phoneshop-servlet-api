package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.cart.Cart;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private Order order;

    @Mock
    private Cart cart;

    @Mock
    private InformationAboutClient informationAboutClient;

    @Mock
    private DeliverySystem deliverySystem;

    @Before
    public void setup() {
        orderService = HttpSessionOrderService.getInstance();
        when(order.getId()).thenReturn(UUID.randomUUID().toString());
        when(order.getPriceOfProducts()).thenReturn(BigDecimal.TEN);
        when(order.getDeliveryPrice()).thenReturn(BigDecimal.ONE);
    }

    @Test
    public void testCreateOrder() {
        Order order = orderService.createOrder(cart, informationAboutClient, deliverySystem, PaymentSystem.CASH);
        assertNotNull(order);
        assertNotNull(order.getInformationAboutClient());
        assertNotNull(order.getDeliverySystem());
        assertNotNull(order.getPaymentSystem());
    }

    @Test
    public void testPlaceOrder() {
        orderService.placeOrder(order, cart);
        verify(order).setId(anyString());
        verify(order).setTotalPrice(new BigDecimal(11));
    }
}
