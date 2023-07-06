package com.acme.test01.givimdinaradze.model;

import com.acme.test01.givimdinaradze.exceptions.AccountNotFoundException;
import com.acme.test01.givimdinaradze.exceptions.WithdrawalAmountTooLargeException;

public class SavingsAccount extends Account {
    private static final double MINIMUM_BALANCE = 1000.00;

    /**
     * Constructor for creating a SavingsAccount object.
     *
     * @param id             The ID of the account.
     * @param customerNumber The customer number associated with the account.
     * @param balance        The initial balance of the account.
     */
    public SavingsAccount(Long id, String customerNumber, double balance) {
        super(id, customerNumber, balance);
        if (balance < MINIMUM_BALANCE) {
            throw new RuntimeException("Insufficient initial deposit. Minimum deposit required: " + MINIMUM_BALANCE);
        }
    }

    /**
     * Withdraw a specified amount from the account, considering the minimum balance requirement.
     *
     * @param amount The amount to withdraw.
     * @throws AccountNotFoundException        If the account is not found.
     * @throws WithdrawalAmountTooLargeException If the withdrawal amount exceeds the available balance.
     */
    @Override
    public void withdraw(double amount) throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        if (getBalance() - amount < MINIMUM_BALANCE) {
            throw new WithdrawalAmountTooLargeException("Withdrawal amount exceeds the available balance.");
        } else {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn: " + amount);
            System.out.println("New balance: " + getBalance());
        }
    }
}

