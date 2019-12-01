package com.es.phoneshop.web;

import com.es.phoneshop.model.checkout.ArrayListOrderDao;
import com.es.phoneshop.model.checkout.Order;
import com.es.phoneshop.model.checkout.OrderDao;
import com.es.phoneshop.model.exception.OrderNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewServlet extends HttpServlet {
    public static final String ORDER = "order";
    private OrderDao orderDao;

    @Override
    public void init() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Order order = null;
        try {
            order = getOrder(request);
        } catch (OrderNotFoundException e) {
            e.printStackTrace();
        }
        request.setAttribute(ORDER, order);
        request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);
    }

    private Order getOrder(HttpServletRequest request) throws OrderNotFoundException {
        String id = request.getPathInfo().replaceAll("/", "");
        return orderDao.getOrder(id);
    }
}
