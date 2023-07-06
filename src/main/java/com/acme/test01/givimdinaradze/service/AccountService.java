package com.acme.test01.givimdinaradze.service;

import com.acme.test01.givimdinaradze.exceptions.AccountNotFoundException;
import com.acme.test01.givimdinaradze.exceptions.WithdrawalAmountTooLargeException;

public interface AccountService {
    void openSavingsAccount(Long accountId, Long amountToDeposit);

    void openCurrentAccount(Long accountId);

    void withdraw(Long accountId, int amountToWithdraw)
            throws AccountNotFoundException, WithdrawalAmountTooLargeException;

    void deposit(Long accountId, int amountToDeposit)
            throws AccountNotFoundException;

}