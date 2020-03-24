package com.accounts.service.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    protected AccountService accountService;

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable int id) {
        logger.debug("Getting account with id {}", id);
        Optional<Account> accountOpt = accountService.getAccount(id);
        Account account = null;

        if (accountOpt.isPresent()) {
            account = accountOpt.get();
            account.setBalance(
                    accountService.getEuroExchangeRate(account.getBalance(), account.getCurrency())
            );
        }
        return account;
    }
}