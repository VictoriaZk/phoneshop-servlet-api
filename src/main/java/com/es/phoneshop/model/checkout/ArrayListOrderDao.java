package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.exception.OrderNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {
    private static final OrderDao INSTANCE = new ArrayListOrderDao();
    private List<Order> orders;

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Order getOrder(String id) throws OrderNotFoundException {
        return orders.parallelStream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new OrderNotFoundException("There is no order with id = " + id));
    }

    @Override
    public void save(Order order) {
        if (order == null || order.getId() == null) {
            throw new IllegalArgumentException("Order or order id can not be null");
        }
        if (orders.contains(order)) {
            throw new IllegalArgumentException("Order with such id is already exits");
        } else {
            orders.add(order);
        }
    }

    @Override
    public void delete(Order order) {
        orders.remove(order);
    }
}
