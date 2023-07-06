import com.acme.test01.givimdinaradze.exceptions.AccountNotFoundException;
import com.acme.test01.givimdinaradze.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.givimdinaradze.model.Account;
import com.acme.test01.givimdinaradze.model.CurrentAccount;
import com.acme.test01.givimdinaradze.model.SavingsAccount;
import com.acme.test01.givimdinaradze.service.AccountService;
import com.acme.test01.givimdinaradze.service.impl.AccountServiceImpl;
import com.acme.test01.givimdinaradze.storage.SystemDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    private AccountService accountService;
    private SystemDB systemDB;

    @BeforeEach
    public void setUp() {
        accountService = new AccountServiceImpl();
        systemDB = SystemDB.getInstance();
    }

    @Test
    public void testAccountExists() {
        Long existingAccountId = 1L;
        Long nonExistingAccountId = 10L;

        boolean existingAccountExists = systemDB.accountExists(existingAccountId);
        boolean nonExistingAccountExists = systemDB.accountExists(nonExistingAccountId);

        assertTrue(existingAccountExists);
        assertFalse(nonExistingAccountExists);
    }


    @Test
    public void testOpenSavingsAccount() {
        Long accountId = 5L;
        Long amountToDeposit = 3000L;

        accountService.openSavingsAccount(accountId, amountToDeposit);

        Account account = systemDB.getAccountById(accountId);
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(amountToDeposit, (long) account.getBalance());
        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    public void testWithdrawFromSavingsAccount() throws WithdrawalAmountTooLargeException {
        Long accountId = 6L;
        Long amountToDeposit = 3000L;
        int amountToWithdraw = 1500;

        accountService.openSavingsAccount(accountId, amountToDeposit);
        accountService.withdraw(accountId, amountToWithdraw);

        Account account = systemDB.getAccountById(accountId);
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(amountToDeposit - amountToWithdraw, (long) account.getBalance());
        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    public void testOpenCurrentAccount() {
        Long accountId = 7L;

        accountService.openCurrentAccount(accountId);

        Account account = systemDB.getAccountById(accountId);
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(0.0, account.getBalance());
        assertTrue(account instanceof CurrentAccount);
    }
    @Test
    public void testWithdrawFromCurrentAccount() throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        Long accountId = 8L;
        double initialBalance = 1000.0;
        double amountToWithdraw = 2500.0;

        accountService.openCurrentAccount(accountId);
        accountService.deposit(accountId, (int) initialBalance);
        accountService.withdraw(accountId, (int) amountToWithdraw);

        Account account = systemDB.getAccountById(accountId);
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(initialBalance - amountToWithdraw, account.getBalance());
        assertTrue(account instanceof CurrentAccount);
    }

    @Test
    public void testWithdrawFromCurrentAccountWithinBalance() throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        Long accountId = 9L;
        double initialBalance = 1000.0;
        double amountToWithdraw = 500.0;
        double expectedBalance = initialBalance - amountToWithdraw;

        accountService.openCurrentAccount(accountId);
        accountService.deposit(accountId, (int) initialBalance);
        accountService.withdraw(accountId, (int) amountToWithdraw);

        Account account = systemDB.getAccountById(accountId);
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(expectedBalance, account.getBalance());
        assertTrue(account instanceof CurrentAccount);
    }

    @Test
    public void testWithdrawFromSavingsAccountWithInsufficientBalance() {
        Long accountId = 10L;
        Long amountToDeposit = 3000L;
        Long amountToWithdraw = 4000L;

        accountService.openSavingsAccount(accountId, amountToDeposit);

        assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            accountService.withdraw(accountId, Math.toIntExact(amountToWithdraw));
        });
    }

    @Test
    public void testWithdrawFromCurrentAccountWithInsufficientBalance() {
        Long accountId = 11L;
        double initialBalance = 1000.0;
        double amountToWithdraw = 2500000.0;

        accountService.openCurrentAccount(accountId);
        accountService.deposit(accountId, (int) initialBalance);

        assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            accountService.withdraw(accountId, (int) amountToWithdraw);
        });
    }

    @Test
    public void testWithdrawFromNonexistentAccount() {
        Long accountId = 12L;
        int amountToWithdraw = 500;

        assertThrows(AccountNotFoundException.class, () -> {
            accountService.withdraw(accountId, amountToWithdraw);
        });
    }

    @Test
    public void testDepositToSavingsAccount() {
        Long accountId = 13L;
        Long amountToDeposit = 2000L;
        Long additionalAmountToDeposit = 1000L;
        Long expectedBalance = amountToDeposit + additionalAmountToDeposit;

        accountService.openSavingsAccount(accountId, amountToDeposit);
        accountService.deposit(accountId, Math.toIntExact(additionalAmountToDeposit));

        Account account = systemDB.getAccountById(accountId);
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(expectedBalance, (long) account.getBalance());
        assertTrue(account instanceof SavingsAccount);
    }

    @Test
    public void testWithdrawFromCurrentAccountWithNegativeBalance() throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        Long accountId = 17L;
        double initialBalance = 1.0;
        double amountToWithdraw = 201.0;

        accountService.openCurrentAccount(accountId);
        accountService.deposit(accountId, (int) initialBalance);

        accountService.withdraw(accountId, (int) amountToWithdraw);

        Account account = systemDB.getAccountById(accountId);
        assertNotNull(account);
        assertEquals(accountId, account.getId());
        assertEquals(initialBalance - amountToWithdraw, account.getBalance());
        assertTrue(account instanceof CurrentAccount);
    }


}
