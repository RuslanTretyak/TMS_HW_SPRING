package org.bank;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BankAccountManagerTest {
    private @InjectMocks BankAccountManager manager;
    private @Mock BankAccountHelper helper;
    private @Mock BankAccount fromAccount;
    private @Mock BankAccount toAccount;

    @Test
    public void transferMoney() {
        when(helper.isBalanceValidForWithdraw(fromAccount, toAccount, 100.0)).thenReturn(true);
    }

    @Test
    public void withdrawMoney() {
    }

    @Test
    public void addMoney() {
    }
}