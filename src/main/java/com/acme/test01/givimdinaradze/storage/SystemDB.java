package com.acme.test01.givimdinaradze.storage;

import com.acme.test01.givimdinaradze.exceptions.AccountNotFoundException;
import com.acme.test01.givimdinaradze.model.CurrentAccount;
import com.acme.test01.givimdinaradze.model.SavingsAccount;
import com.acme.test01.givimdinaradze.model.Account;

import java.util.HashMap;
import java.util.Map;

public class SystemDB {
    private static SystemDB instance;
    private Map<Long, Account> accounts;

    private SystemDB() {
        accounts = new HashMap<>();

        // Pre-populate the database with the given accounts
        accounts.put(1L, new SavingsAccount(1L, "1", 2000.0));
        accounts.put(2L, new SavingsAccount(2L, "2", 5000.0));
        accounts.put(3L, new CurrentAccount(3L, "3", 1000.0, 10000.0)); // overdraft limit: R10,000.00
        accounts.put(4L, new CurrentAccount(4L, "4", -5000.0, 20000.0)); // overdraft limit: R20,000.00
    }

    public static SystemDB getInstance() {
        if (instance == null) {
            synchronized (SystemDB.class) {
                if (instance == null) {
                    instance = new SystemDB();
                }
            }
        }
        return instance;
    }

    /**
     * Add an account to the database.
     *
     * @param account The account to add.
     */
    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    /**
     * Get the account with the specified ID from the database.
     *
     * @param accountId The ID of the account to retrieve.
     * @return The account with the specified ID.
     * @throws AccountNotFoundException If the account with the given ID does not exist.
     */
    public Account getAccountById(Long accountId) throws AccountNotFoundException {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account not found with ID: " + accountId);
        }
        return account;
    }


    /**
     * Check if an account with the specified ID exists in the database.
     *
     * @param accountId The ID of the account to check.
     * @return True if the account exists, false otherwise.
     */
    public boolean accountExists(Long accountId) {
        return accounts.containsKey(accountId);
    }

}
