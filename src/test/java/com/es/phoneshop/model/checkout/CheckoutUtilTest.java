package com.es.phoneshop.model.checkout;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CheckoutUtilTest {

    @Test
    public void testValidPhone() {
        assertTrue(CheckoutUtil.isValidPhone("+123456789012"));
    }

    @Test
    public void testInvalidPhoneWithoutPlusSymbol() {
        assertTrue(!CheckoutUtil.isValidPhone("123456789012"));
    }

    @Test
    public void testInvalidPhoneWithIncorrectSymbols() {
        assertTrue(!CheckoutUtil.isValidPhone("+12345678901a"));
    }

    @Test
    public void testInvalidPhoneWithSymbolsAmountLessNeeded() {
        assertTrue(!CheckoutUtil.isValidPhone("+12345678901"));
    }

    @Test
    public void testInvalidPhoneWithSymbolsAmountGreaterNeeded() {
        assertTrue(!CheckoutUtil.isValidPhone("+1234567890123"));
    }

    @Test
    public void testValidFirstName() {
        assertTrue(CheckoutUtil.isValidFirstName("vika"));
    }

    @Test
    public void testValidLastName() {
        assertTrue(CheckoutUtil.isValidLastName("Zhak"));
    }

    @Test
    public void testInvalidLastNameWithNumberSymbol() {
        assertFalse(CheckoutUtil.isValidLastName("Z78db"));
    }

}
