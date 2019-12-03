package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.exception.OrderNotFoundException;

public interface OrderDao {
    Order getOrder(String id) throws OrderNotFoundException;

    void save(Order order);

    void delete(Order order);
}
