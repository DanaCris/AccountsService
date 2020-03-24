package com.accounts.service.account;

import com.accounts.service.utils.CurrencyUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CurrencyUtils currencyUtils;

    public Optional<Account> getAccount(Integer id) {
        return accountRepository.findById(id);
    }

    public double getEuroExchangeRate(double amount, String currency) {
        double convertedAmount = 0;
        JsonNode rates = currencyUtils.getExchangeRates();

        if (rates != null && rates.get(currency) != null) {
            convertedAmount = amount / rates.get(currency).asDouble();
        }
        return convertedAmount;
    }
}
