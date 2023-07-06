package com.acme.test01.givimdinaradze.model;

import com.acme.test01.givimdinaradze.exceptions.AccountNotFoundException;
import com.acme.test01.givimdinaradze.exceptions.WithdrawalAmountTooLargeException;

import java.util.Objects;

public abstract class Account {
    private Long id;
    private String customerNumber;
    private double balance;

    /**
     * Constructor for creating an Account object.
     *
     * @param id             The ID of the account.
     * @param customerNumber The customer number associated with the account.
     * @param balance        The initial balance of the account.
     */
    public Account(Long id, String customerNumber, double balance) {
        this.id = id;
        this.customerNumber = customerNumber;
        this.balance = balance;
    }

    /**
     * Get the ID of the account.
     *
     * @return The account ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the customer number associated with the account.
     *
     * @return The customer number.
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * Get the balance of the account.
     *
     * @return The account balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Set the balance of the account.
     *
     * @param balance The new balance to set.
     */
    protected void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Abstract method for withdrawing a specified amount from the account.
     *
     * @param amount The amount to withdraw.
     * @throws AccountNotFoundException        If the account is not found.
     * @throws WithdrawalAmountTooLargeException If the withdrawal amount exceeds the available balance.
     */
    public abstract void withdraw(double amount) throws AccountNotFoundException, WithdrawalAmountTooLargeException;

    /**
     * Deposit the specified amount to the account.
     *
     * @param amount The amount to deposit.
     * @throws AccountNotFoundException If the account is not found.
     */
    public void deposit(double amount) throws AccountNotFoundException {
        balance += amount;
        System.out.println("Deposited: " + amount);
        System.out.println("New balance: " + balance);
    }

    /**
     * Check if the current account is equal to another account based on their IDs.
     *
     * @param o The other object to compare.
     * @return True if the accounts are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    /**
     * Generate the hash code for the account based on its ID.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
