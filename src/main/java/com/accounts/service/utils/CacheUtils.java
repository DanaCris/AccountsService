package com.accounts.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;

public class CacheUtils {
    @Autowired
    CacheManager cacheManager;

    @Scheduled(cron = "* 0 * * * * ")
    @CacheEvict(value = "exchangeRates")
    private void evictExchangeRatesCache() {
        cacheManager.getCache("exchangeRates").clear();
    }
}
