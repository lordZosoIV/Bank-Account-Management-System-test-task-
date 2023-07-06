package com.acme.test01.givimdinaradze;

import com.acme.test01.givimdinaradze.exceptions.AccountNotFoundException;
import com.acme.test01.givimdinaradze.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.givimdinaradze.service.AccountService;
import com.acme.test01.givimdinaradze.service.impl.AccountServiceImpl;

public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountServiceImpl();

        Long additionalSavingsAccountId = 5L;
        Long additionalCurrentAccountId = 6L;
        Long additionalSavingsAmountToDeposit = 1500L;

        accountService.openSavingsAccount(additionalSavingsAccountId, additionalSavingsAmountToDeposit);
        accountService.openCurrentAccount(additionalCurrentAccountId);

        System.out.println("Accounts with IDs 5 and 6 created.");

        try {
            // Perform a withdrawal from the additional savings account
            int savingsWithdrawalAmount = 300;
            accountService.withdraw(additionalSavingsAccountId, savingsWithdrawalAmount);

            // Perform a deposit to the additional current account
            int currentDepositAmount = 2000;
            accountService.deposit(additionalCurrentAccountId, currentDepositAmount);

            // Perform another withdrawal from the additional savings account
            int savingsWithdrawalAmount2 = 100;
            accountService.withdraw(additionalSavingsAccountId, savingsWithdrawalAmount2);

            // Perform another deposit to the additional current account
            int currentDepositAmount2 = 500;
            accountService.deposit(additionalCurrentAccountId, currentDepositAmount2);
        } catch (AccountNotFoundException e) {
            System.out.println("Account not found: " + e.getMessage());
        } catch (WithdrawalAmountTooLargeException e) {
            System.out.println("Withdrawal amount too large: " + e.getMessage());
        }
    }
}
