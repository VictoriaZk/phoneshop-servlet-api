package com.es.phoneshop.model.DOSProtection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DOSProtectionServiceTest {
    private DOSProtectionService service;

    @Before
    public void setup() {
        service = DOSProtectionServiceImpl.getInstance();
    }

    @Test
    public void testPositiveResult() {
        String ip = "1";
        assertTrue(service.isAllowed(ip));
    }

    @Test
    public void testNegativeResult() {
        String ip = "2";
        for (int i = 0; i < 20; i++) {
            service.isAllowed(ip);
        }
        assertTrue(!service.isAllowed(ip));
    }
}
