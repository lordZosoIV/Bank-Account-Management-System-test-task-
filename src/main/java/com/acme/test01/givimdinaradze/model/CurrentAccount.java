package com.acme.test01.givimdinaradze.model;

import com.acme.test01.givimdinaradze.exceptions.AccountNotFoundException;
import com.acme.test01.givimdinaradze.exceptions.WithdrawalAmountTooLargeException;

public class CurrentAccount extends Account {
    private static final double MAX_OVERDRAFT_LIMIT = 100000.0;

    private double overdraftLimit;

    /**
     * Constructor for creating a CurrentAccount object with a specified overdraft limit.
     *
     * @param id             The ID of the account.
     * @param customerNumber The customer number associated with the account.
     * @param balance        The initial balance of the account.
     * @param overdraftLimit The maximum overdraft limit allowed on the account.
     */
    public CurrentAccount(Long id, String customerNumber, double balance, double overdraftLimit) {
        super(id, customerNumber, balance);
        if (overdraftLimit > MAX_OVERDRAFT_LIMIT) {
            throw new RuntimeException("Invalid overdraft limit: " + overdraftLimit);
        }
        this.overdraftLimit = overdraftLimit;
    }

    /**
     * Constructor for creating a CurrentAccount object with the default overdraft limit.
     *
     * @param id             The ID of the account.
     * @param customerNumber The customer number associated with the account.
     * @param balance        The initial balance of the account.
     */
    public CurrentAccount(Long id, String customerNumber, double balance) {
        super(id, customerNumber, balance);
        this.overdraftLimit = MAX_OVERDRAFT_LIMIT;
    }

    /**
     * Withdraw a specified amount from the account, considering the available balance and overdraft limit.
     *
     * @param amount The amount to withdraw.
     * @throws AccountNotFoundException        If the account is not found.
     * @throws WithdrawalAmountTooLargeException If the withdrawal amount exceeds the available balance with overdraft.
     */
    @Override
    public void withdraw(double amount) throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        double availableBalance = getBalance() + overdraftLimit;
        if (amount <= availableBalance) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn: " + amount);
            System.out.println("New balance: " + getBalance());
        } else {
            throw new WithdrawalAmountTooLargeException("Withdrawal amount exceeds the available balance with overdraft.");
        }
    }
}
