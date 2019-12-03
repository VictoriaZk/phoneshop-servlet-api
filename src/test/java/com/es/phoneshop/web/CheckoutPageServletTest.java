package com.es.phoneshop.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {

    private static final String FIRST_NAME_PARAMETER = "firstName";
    private static final String LAST_NAME_PARAMETER = "lastName";
    private static final String PHONE_PARAMETER = "phone";
    private static final String ADDRESS_PARAMETER = "address";
    private static final String PAYMENT_SYSTEM = "paymentSystem";
    private static final String DELIVERY_MODE = "deliveryMode";
    private static final String DATE = "date";
    private static final String TODAY = "Today";
    private static final String COURIER = "Courier";
    private static final String CARD = "Card";
    private static final String PHONE_NUMBER = "+375298325741";
    public static final String CONTEXT_PATH = "contextPath";
    public static final String FIRST_NAME = "fgh";
    public static final String LAST_NAME = "fghjk";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession httpSession;

    private CheckoutPageServlet servlet;

    @Before
    public void setup() {
        servlet = new CheckoutPageServlet();
        servlet.init();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter(PAYMENT_SYSTEM)).thenReturn(CARD);
        when(request.getParameter(DELIVERY_MODE)).thenReturn(COURIER);
        when(request.getParameter(DATE)).thenReturn(TODAY);
        when(request.getContextPath()).thenReturn(CONTEXT_PATH);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);
        verify(request, times(4)).setAttribute(anyString(), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostIncorrectData() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(request, times(12)).setAttribute(anyString(), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostCorrectData() throws ServletException, IOException {
        when(request.getParameter(FIRST_NAME_PARAMETER)).thenReturn(FIRST_NAME);
        when(request.getParameter(LAST_NAME_PARAMETER)).thenReturn(LAST_NAME);
        when(request.getParameter(PHONE_PARAMETER)).thenReturn(PHONE_NUMBER);
        when(request.getParameter(ADDRESS_PARAMETER)).thenReturn(ADDRESS_PARAMETER);
        servlet.doPost(request, response);
        verify(response).sendRedirect(anyString());
    }
}