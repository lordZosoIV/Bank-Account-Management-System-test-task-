package com.acme.test01.givimdinaradze.service.impl;

import com.acme.test01.givimdinaradze.exceptions.AccountNotFoundException;
import com.acme.test01.givimdinaradze.model.Account;
import com.acme.test01.givimdinaradze.model.CurrentAccount;
import com.acme.test01.givimdinaradze.model.SavingsAccount;
import com.acme.test01.givimdinaradze.storage.SystemDB;
import com.acme.test01.givimdinaradze.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.givimdinaradze.service.AccountService;

public class AccountServiceImpl implements AccountService {
    private SystemDB systemDB;

    public AccountServiceImpl() {
        systemDB = SystemDB.getInstance();
    }

    /**
     * Open a savings account with the specified ID and initial deposit.
     *
     * @param accountId       The ID of the account to open.
     * @param amountToDeposit The initial deposit amount.
     */
    @Override
    public void openSavingsAccount(Long accountId, Long amountToDeposit) {
        if (systemDB.accountExists(accountId)) {
            throw new RuntimeException("Account with ID " + accountId + " already exists.");
        }
        Account account = new SavingsAccount(accountId, accountId.toString(), amountToDeposit);
        systemDB.addAccount(account);
        System.out.println("Savings Account opened with ID: " + accountId);
        System.out.println("Initial deposit: " + amountToDeposit);
    }

    /**
     * Open a current account with the specified ID.
     *
     * @param accountId The ID of the account to open.
     */
    @Override
    public void openCurrentAccount(Long accountId) {
        if (systemDB.accountExists(accountId)) {
            throw new RuntimeException("Account with ID " + accountId + " already exists.");
        }
        Account account = new CurrentAccount(accountId, accountId.toString(), 0.0);
        systemDB.addAccount(account);
        System.out.println("Current Account opened with ID: " + accountId);
    }

    /**
     * Withdraw the specified amount from the account with the given ID.
     *
     * @param accountId       The ID of the account to withdraw from.
     * @param amountToWithdraw The amount to withdraw.
     * @throws AccountNotFoundException        If the account with the given ID does not exist.
     * @throws WithdrawalAmountTooLargeException If the withdrawal amount exceeds the available balance.
     */
    @Override
    public void withdraw(Long accountId, int amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        Account account = systemDB.getAccountById(accountId);
        account.withdraw(amountToWithdraw);
    }

    /**
     * Deposit the specified amount to the account with the given ID.
     *
     * @param accountId      The ID of the account to deposit to.
     * @param amountToDeposit The amount to deposit.
     * @throws AccountNotFoundException If the account with the given ID does not exist.
     */
    @Override
    public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {
        Account account = systemDB.getAccountById(accountId);
        account.deposit(amountToDeposit);
    }
}
