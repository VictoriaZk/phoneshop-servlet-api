package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.IllegalQuantityException;
import com.es.phoneshop.model.exception.LackOfStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private HttpSession httpSession;

    @Mock
    private HttpServletRequest request;
    private ProductDao productDao;
    private CartService cartService;
    private Product product;

    @Before
    public void setup() {
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        request = Mockito.mock(HttpServletRequest.class);
        httpSession = Mockito.mock(HttpSession.class);
        Mockito.when(request.getSession()).thenReturn(httpSession);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getLocale()).thenReturn(new Locale("en", "USA"));
    }

    @Test
    public void testGetExistingCart() {
        Cart cart = new Cart();
        when(httpSession.getAttribute(anyString())).thenReturn(cart);
        Cart cartFromSession = cartService.getCart(httpSession);
        assertEquals(cart, cartFromSession);
    }

    @Test
    public void testGetNotExistingCart() {
        Cart cart = cartService.getCart(httpSession);
        assertNotNull(cart);
    }

    @Test
    public void testAdd() {
        Cart cart = cartService.getCart(request.getSession());
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        cartService.add(httpSession, cart, product.getId(), String.valueOf(1), request.getLocale());
        assertNotNull(cart);
        assertTrue(cart.getCartItems().size() == 1);
        assertEquals(cart.getCartItems().get(0).getProduct(), product);
        assertEquals(cart.getCartItems().get(0).getQuantity(), 1);
        assertEquals(cart.getTotalQuantity(), 1);
        assertEquals(cart.getTotalPrice(), new BigDecimal(100));
    }

    @Test(expected = LackOfStockException.class)
    public void testAddWithStockLessQuantity() throws LackOfStockException, IllegalQuantityException {
        Cart cart = cartService.getCart(request.getSession());
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(16L, "sgs", "Samsung7 Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);
        cartService.add(httpSession, cart, product.getId(), String.valueOf(150), request.getLocale());
    }


    @Test
    public void testDeleteCartItem() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg");
        productDao.save(product1);
        Product product2 = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Cart cart = cartService.getCart(request.getSession());
        cartService.add(httpSession, cart, product1.getId(), String.valueOf(1), request.getLocale());
        cartService.add(httpSession, cart, product2.getId(), String.valueOf(1), request.getLocale());
        assertNotNull(cart);
        assertEquals(cart.getCartItems().size(), 2);
        cartService.delete(cart, product1);
        assertNotNull(cart);
        assertEquals(cart.getCartItems().size(), 1);
        assertEquals(cart.getCartItems().get(0).getQuantity(), 1);
    }

    @Test
    public void testCalculateTotalPrice() {
        Cart cart = cartService.getCart(request.getSession());
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(15L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        cart.getCartItems().add(new CartItem(product, 10));
        cartService.calculateTotalPrice(cart);
        assertEquals(new BigDecimal(1000), cart.getTotalPrice());
    }

    @Test
    public void testCalculateTotalQuantity() {
        Cart cart = cartService.getCart(request.getSession());
        cart.getCartItems().add(new CartItem(product, 7));
        cart.getCartItems().add(new CartItem(product, 2));
        cartService.calculateTotalQuantity(cart);
        assertEquals(9, cart.getTotalQuantity());
    }
}

