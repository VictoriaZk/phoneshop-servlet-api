package com.es.phoneshop.model.checkout;

import java.util.regex.Pattern;

public class CheckoutUtil {
    private CheckoutUtil() {
    }

    public static boolean isValidFirstName(String firstName) {
        String firstNamePattern = "[A-Z][a-z]+([\\-][A-Z][a-z]+)*";
        return Pattern.compile(firstNamePattern).matcher(firstName).matches();
    }

    public static boolean isValidLastName(String lastName) {
        String lastNamePattern = "[A-Z][a-z]+([\\-][A-Z][a-z]+)*";
        return Pattern.compile(lastNamePattern).matcher(lastName).matches();
    }

    public static boolean isValidPhone(String numberPhone) {
        String numberPhonePattern = "[+]\\d{12}";
        return Pattern.compile(numberPhonePattern).matcher(numberPhone).matches();
    }
}
