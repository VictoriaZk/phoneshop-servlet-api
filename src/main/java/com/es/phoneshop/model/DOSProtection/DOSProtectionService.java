package com.es.phoneshop.model.DOSProtection;


public interface DOSProtectionService {
    boolean isAllowed(String ip);
}