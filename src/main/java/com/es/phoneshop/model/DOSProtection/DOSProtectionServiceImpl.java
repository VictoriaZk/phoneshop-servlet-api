package com.es.phoneshop.model.DOSProtection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DOSProtectionServiceImpl implements DOSProtectionService {

    private static final DOSProtectionService INSTANCE = new DOSProtectionServiceImpl();

    private final Map<String, AtomicInteger> amountOfRequestsPerIp = new ConcurrentHashMap<>();

    private volatile long lastCleaningTime = System.currentTimeMillis();

    private static final int LIMIT_REQUESTS_PER_IP = 200;

    private static final int MINUTE = 60_000;

    public static DOSProtectionService getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isAllowed(String ip) {
        refreshCache();
        return findAmountOfRequests(ip) <= LIMIT_REQUESTS_PER_IP;
    }

    private int findAmountOfRequests(String ip) {
        return amountOfRequestsPerIp.computeIfAbsent(ip, k -> new AtomicInteger(0)).incrementAndGet();
    }

    private void refreshCache() {
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - lastCleaningTime;
        if (delta >= MINUTE) {
            amountOfRequestsPerIp.clear();
            lastCleaningTime = currentTime;
        }
    }
}