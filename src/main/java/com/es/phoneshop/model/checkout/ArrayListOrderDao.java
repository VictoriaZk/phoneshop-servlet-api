package com.es.phoneshop.model.checkout;

import com.es.phoneshop.model.exception.OrderNotFoundException;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {
    private static volatile OrderDao INSTANCE;
    private List<Order> orders;

    private ArrayListOrderDao(){

    }

    public static OrderDao getInstance() {
        if (INSTANCE == null) {
            synchronized (ArrayListOrderDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ArrayListOrderDao();
                }
            }
        }
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
        Order orderWithSameId;
        try {
            orderWithSameId = getOrder(order.getId());
        } catch (OrderNotFoundException exception) {
            orderWithSameId = null;
        }

        if (orderWithSameId == null) {
            orders.add(order);
        } else {
            throw new IllegalArgumentException("Order with such id is already exists");
        }
    }

    @Override
    public void delete(Order order) {
        orders.remove(order);
    }
}