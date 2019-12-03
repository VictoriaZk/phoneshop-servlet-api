package com.es.phoneshop.web;

import com.es.phoneshop.model.checkout.ArrayListOrderDao;
import com.es.phoneshop.model.checkout.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewServletTest {

    public static final String ORDER = "order";
    private OrderOverviewServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private Order order;

    @Before
    public void setup() {
        servlet = new OrderOverviewServlet();
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        String id = UUID.randomUUID().toString();
        when(request.getPathInfo()).thenReturn("/" + id);
        when(order.getId()).thenReturn(id);
    }

    @After
    public void destroy() {
        ArrayListOrderDao.getInstance().delete(order);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        ArrayListOrderDao.getInstance().save(order);
        servlet.init();
        servlet.doGet(request, response);
        verify(request).setAttribute(ORDER, order);
        verify(request.getRequestDispatcher(anyString())).forward(request, response);
    }
}