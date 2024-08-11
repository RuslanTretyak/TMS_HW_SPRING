package org.bank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountManagerTest {
    private @InjectMocks BankAccountManager manager;
    private @Mock BankAccountHelper helper;
    private BankAccount fromAccount;
    private BankAccount toAccount;

    @Test
    public void transferMoney_to() throws InvalidBankOperationException {
        fromAccount = new BankAccount();
        fromAccount.setAccountCurrency("USD");
        toAccount = new BankAccount();
        toAccount.setAccountCurrency("USD");
        when(helper.isBalanceValidForWithdraw(fromAccount, 100.0, "USD")).thenReturn(true);
        when(helper.isAvailableForDailyWithdraw(fromAccount, 100.0)).thenReturn(true);
        when(helper.convertCurrency(fromAccount.getAccountCurrency(), toAccount.getAccountCurrency(), 100.0)).thenReturn(100.0);
        fromAccount.setCurrentBalance(100.0);
        toAccount.setCurrentBalance(0);
        manager.transferMoney(fromAccount, toAccount, 100);
        assertEquals(100.0, toAccount.getCurrentBalance(), 0.0);
    }
    @Test
    public void transferMoney_from() throws InvalidBankOperationException {
        fromAccount = new BankAccount();
        fromAccount.setAccountCurrency("USD");
        toAccount = new BankAccount();
        toAccount.setAccountCurrency("USD");
        when(helper.isBalanceValidForWithdraw(fromAccount, 100.0, "USD")).thenReturn(true);
        when(helper.isAvailableForDailyWithdraw(fromAccount, 100.0)).thenReturn(true);
        when(helper.convertCurrency(fromAccount.getAccountCurrency(), toAccount.getAccountCurrency(), 100.0)).thenReturn(100.0);
        fromAccount.setCurrentBalance(100.0);
        toAccount.setCurrentBalance(0);
        manager.transferMoney(fromAccount, toAccount, 100);
        assertEquals(0.0, fromAccount.getCurrentBalance(), 0.0);
    }
    @Test(expected = InvalidBankOperationException.class)
    public void transferMoney_exception() throws InvalidBankOperationException {
        fromAccount = new BankAccount();
        fromAccount.setAccountCurrency("USD");
        toAccount = new BankAccount();
        toAccount.setAccountCurrency("USD");
        when(helper.isBalanceValidForWithdraw(fromAccount, 100.0, "USD")).thenReturn(false);
        manager.transferMoney(fromAccount, toAccount, 100);
    }

    @Test
    public void withdrawMoney_balance() throws InvalidBankOperationException {
        fromAccount = new BankAccount();
        fromAccount.setAccountCurrency("USD");
        when(helper.isBalanceValidForWithdraw(fromAccount, 100.0, "USD")).thenReturn(true);
        when(helper.isAvailableForDailyWithdraw(fromAccount, 100.0)).thenReturn(true);
        when(helper.convertCurrency(fromAccount.getAccountCurrency(), "USD", 100.0)).thenReturn(100.0);
        fromAccount.setCurrentBalance(100.0);
        manager.withdrawMoney(fromAccount, 100.0, "USD");
        assertEquals(0.0, fromAccount.getCurrentBalance(), 0.0);
    }
    @Test
    public void withdrawMoney_limit() throws InvalidBankOperationException {
        fromAccount = new BankAccount();
        fromAccount.setAccountCurrency("USD");
        when(helper.isBalanceValidForWithdraw(fromAccount, 100.0, "USD")).thenReturn(true);
        when(helper.isAvailableForDailyWithdraw(fromAccount, 100.0)).thenReturn(true);
        when(helper.convertCurrency(fromAccount.getAccountCurrency(), "USD", 100.0)).thenReturn(100.0);
        fromAccount.setDailyLimit(100.0);
        manager.withdrawMoney(fromAccount, 100.0, "USD");
        assertEquals(0.0, fromAccount.getDailyLimit(), 0.0);
    }
    @Test(expected = InvalidBankOperationException.class)
    public void withdrawMoney_exception() throws InvalidBankOperationException {
        fromAccount = new BankAccount();
        fromAccount.setAccountCurrency("USD");
        when(helper.isBalanceValidForWithdraw(fromAccount, 100.0, "USD")).thenReturn(false);
        manager.withdrawMoney(fromAccount, 100.0, "USD");
    }

    @Test
    public void addMoney() {
        toAccount = new BankAccount();
        toAccount.setCurrentBalance(0.0);
        when(helper.convertCurrency("USD", toAccount.getAccountCurrency(), 100.0)).thenReturn(100.0);
        manager.addMoney(toAccount, 100.0, "USD");
        assertEquals(100.0, toAccount.getCurrentBalance(), 0.0);
    }
}