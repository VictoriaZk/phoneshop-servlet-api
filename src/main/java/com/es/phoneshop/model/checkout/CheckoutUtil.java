package com.es.phoneshop.model.checkout;

import java.util.regex.Pattern;

public class CheckoutUtil {

    public static final String REGEX_NAME = "[A-Z]?[a-z]+([\\-][a-z]+)*";
    public static final String REGEX_PHONE = "[+]\\d{12}";

    private CheckoutUtil() {
    }

    public static boolean isValidFirstName(String firstName) {
        String firstNamePattern = REGEX_NAME;
        return Pattern.compile(firstNamePattern).matcher(firstName).matches();
    }

    public static boolean isValidLastName(String lastName) {
        String lastNamePattern = REGEX_NAME;
        return Pattern.compile(lastNamePattern).matcher(lastName).matches();
    }

    public static boolean isValidPhone(String numberPhone) {
        String numberPhonePattern = REGEX_PHONE;
        return Pattern.compile(numberPhonePattern).matcher(numberPhone).matches();
    }
}
