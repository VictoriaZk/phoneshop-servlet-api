package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.exception.OrderNotFoundException;
import java.util.List;

public interface OrderDao {
    Order getOrder(String id) throws OrderNotFoundException;
    void save(Order order);
    void delete(Order order);
}
