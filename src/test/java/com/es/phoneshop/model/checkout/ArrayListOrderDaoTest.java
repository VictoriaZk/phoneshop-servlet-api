package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.exception.OrderNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListOrderDaoTest {

    private OrderDao orderDao;

    @Mock
    private Order order;

    @Before
    public void setup() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Test
    public void testSaveOrder() throws OrderNotFoundException {
        String id = UUID.randomUUID().toString();
        when(order.getId()).thenReturn(id);
        orderDao.save(order);
        assertEquals(orderDao.getOrder(id), order);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNullOrder() {
        orderDao.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveProductWithSameId() {
        String id = UUID.randomUUID().toString();
        when(order.getId()).thenReturn(id);
        orderDao.save(order);
        orderDao.save(order);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testDeleteOrder() throws OrderNotFoundException {
        String id = UUID.randomUUID().toString();
        when(order.getId()).thenReturn(id);
        orderDao.save(order);
        orderDao.delete(order);
        orderDao.getOrder(id);
    }
}