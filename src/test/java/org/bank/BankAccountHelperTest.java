package org.bank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountHelperTest {
    private @InjectMocks BankAccountHelper bankAccountHelper;
    private @Mock BankAccount account;

    @Test
    public void convertCurrency_UsdToUsd() {
        double result = bankAccountHelper.convertCurrency("USD", "USD", 100.0);
        assertEquals(100.0, result, 0.0);
    }
    @Test
    public void convertCurrency_UsdToEur() {
        double result = bankAccountHelper.convertCurrency("USD", "EUR", 100.0);
        assertEquals(94.0, result, 0.0);
    }
    @Test
    public void convertCurrency_UsdToByn() {
        double result = bankAccountHelper.convertCurrency("USD", "BYN", 100.0);
        assertEquals(318.0, result, 0.0);
    }

    @Test
    public void isBalanceValidForWithdraw_true() {
        when(account.getAccountCurrency()).thenReturn("USD");
        when(account.getCurrentBalance()).thenReturn(100.0);
        boolean result = bankAccountHelper.isBalanceValidForWithdraw(account, 100, "USD");
        assertTrue(result);
    }
    @Test
    public void isBalanceValidForWithdraw_false() {
        when(account.getAccountCurrency()).thenReturn("USD");
        when(account.getCurrentBalance()).thenReturn(99.0);
        boolean result = bankAccountHelper.isBalanceValidForWithdraw(account, 100, "USD");
        assertFalse(result);
    }

    @Test
    public void isAvailableForDailyWithdraw_true() {
        when(account.getDailyLimit()).thenReturn(100.0);
        boolean result = bankAccountHelper.isAvailableForDailyWithdraw(account, 100.0);
        assertTrue(result);
    }
    @Test
    public void isAvailableForDailyWithdraw_false() {
        when(account.getDailyLimit()).thenReturn(99.0);
        boolean result = bankAccountHelper.isAvailableForDailyWithdraw(account, 100.0);
        assertFalse(result);
    }
}