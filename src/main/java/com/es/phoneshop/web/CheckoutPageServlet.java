package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.checkout.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CheckoutPageServlet extends HttpServlet {
    public static final String CART = "cart";
    private static final String FIRST_NAME_PARAMETER = "firstName";
    private static final String LAST_NAME_PARAMETER = "lastName";
    private static final String PHONE_PARAMETER = "phone";
    private static final String ADDRESS_PARAMETER = "address";
    public static final String DELIVERY_MODES = "deliveryModes";
    public static final String DATES = "dates";
    public static final String PAYMENT_SYSTEMS = "paymentSystems";
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = HttpSessionOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        request.setAttribute(CART, cart);
        request.setAttribute(DELIVERY_MODES, orderService.getDeliveryModes());
        request.setAttribute(DATES, orderService.getDeliveryDates());
        request.setAttribute(PAYMENT_SYSTEMS, orderService.getPaymentSystems());
        request.getRequestDispatcher("/WEB-INF/pages/checkoutPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<Order> optionalOrder = createOrder(request);
        if (optionalOrder.isPresent()) {
            String orderOverviewPage = request.getContextPath() + "/order/overview/" + optionalOrder.get().getId();
            response.sendRedirect(orderOverviewPage);
        } else {
            fillOrderInformation(request);
            doGet(request, response);
        }
    }

    private Optional<Order> createOrder(HttpServletRequest request) {
        Optional<InformationAboutClient> optionalContactDetails = getInformationAboutClientFromRequest(request);
        Optional<DeliverySystem> optionalDeliveryDetails = getDeliverySystemFromRequest(request);
        PaymentSystem paymentSystem = getPaymentSystemFormRequest(request);
        if (optionalContactDetails.isPresent() && optionalDeliveryDetails.isPresent()) {
            Cart cart = cartService.getCart(request.getSession());
            Order order = orderService.createOrder(cart, optionalContactDetails.get(),
                    optionalDeliveryDetails.get(), paymentSystem);
            orderService.placeOrder(order, cart);
            return Optional.of(order);
        } else {
            return Optional.empty();
        }
    }

    private void fillOrderInformation(HttpServletRequest request) {
        request.setAttribute(FIRST_NAME_PARAMETER, request.getParameter(FIRST_NAME_PARAMETER));
        request.setAttribute(LAST_NAME_PARAMETER, request.getParameter(LAST_NAME_PARAMETER));
        request.setAttribute(PHONE_PARAMETER, request.getParameter(PHONE_PARAMETER));
        request.setAttribute(ADDRESS_PARAMETER, request.getParameter(ADDRESS_PARAMETER));
    }

    private Optional<InformationAboutClient> getInformationAboutClientFromRequest(HttpServletRequest request) {
        boolean isSuccessful = true;
        String firstName = request.getParameter(FIRST_NAME_PARAMETER);
        if (isIncorrect(firstName) || !CheckoutUtil.isValidFirstName(firstName)) {
            isSuccessful = false;
            request.setAttribute("firstNameError", "Incorrect first name");
        }
        String lastName = request.getParameter(LAST_NAME_PARAMETER);
        if (isIncorrect(lastName) || !CheckoutUtil.isValidLastName(lastName)) {
            isSuccessful = false;
            request.setAttribute("lastNameError", "Incorrect last name");
        }
        String phone = request.getParameter(PHONE_PARAMETER);
        if (isIncorrect(phone) || !CheckoutUtil.isValidPhone(phone)) {
            isSuccessful = false;
            request.setAttribute("phoneError", "Phone number should contain symbol '+' and 12 digits");
        }
        if (isSuccessful) {
            return Optional.of(new InformationAboutClient(firstName, lastName, phone));
        } else {
            return Optional.empty();
        }
    }

    private Optional<DeliverySystem> getDeliverySystemFromRequest(HttpServletRequest request) {
        boolean isSuccessful = true;
        String address = request.getParameter(ADDRESS_PARAMETER);
        if (isIncorrect(address)) {
            isSuccessful = false;
            request.setAttribute("addressError", "Incorrect address");
        }
        if (isSuccessful) {
            DeliveryMode deliveryMode = orderService.getDeliveryMode(request.getParameter("deliveryMode"));
            DeliveryDate deliveryDate = orderService.getDeliveryDate(request.getParameter("date"));
            return Optional.of(new DeliverySystem(deliveryMode, deliveryDate, address));
        } else {
            return Optional.empty();
        }
    }

    private PaymentSystem getPaymentSystemFormRequest(HttpServletRequest request) {
        return orderService.getPaymentSystem(request.getParameter("paymentSystem"));
    }

    private boolean isIncorrect(String value) {
        return value == null || value.isEmpty();
    }
}
