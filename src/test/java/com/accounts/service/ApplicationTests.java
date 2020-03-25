package com.accounts.service;

import com.accounts.service.account.Account;
import com.accounts.service.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

import java.util.Optional;

@SpringBootTest
class ApplicationTests {

    @Autowired
    protected AccountService accountService;

    @Test
    void contextLoads() {
        AssertionErrors.assertNotNull("AccountService was not loaded", accountService);
    }

    @Test
    public void testAccountNotFound() {
        Optional<Account> account = accountService.getAccount(1);
        AssertionErrors.assertTrue("Account not found", account.isPresent());
    }
}
