package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.account.Account;
import org.hubert_lasota.BusinessManagement.employee.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountRepositoryTest {
    AccountRepository accountRepository = AccountRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = AccountRepository.class.getDeclaredField("accounts");
        field.setAccessible(true);
        Map<String, Account> accounts = (Map<String, Account>) field.get(accountRepository);
        accounts.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = accountRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        long counter = 100;
        String username;
        String password;
        for(int i = 0; i < counter; i++) {
            username = "user" + i;
            password = "pass" + i;
            accountRepository.save(new Account(username, password));
        }
        long repositoryCounter = accountRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveAccount() {
        Account actual = new Account("user", "pass");
        long repositorySizeBeforeSave = accountRepository.count();

        Account expected = accountRepository.save(actual);
        long repositorySizeAfterSave = accountRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleAccounts() {
        int counter = 10;
        String username;
        String password;
        for(int i = 0; i < counter; i++) {
            username = "user" + i;
            password = "pass" + i;
            accountRepository.save(new Account(username, password));
        }
        long repositorySizeAfterSave = accountRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteAccountByUsername() {
        Account account = new Account("user", "pass");
        accountRepository.save(account);
        long repositorySizeAfterSave = accountRepository.count();

        accountRepository.delete(account.getUsername());
        long repositorySizeAfterDelete = accountRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete + 1);
    }

    @Test
    void shouldDeleteMultipleAccountsByUsername() {
        int counter = 10;
        String username;
        String password;
        for(int i = 0; i < counter; i++) {
            username = "user" + i;
            password = "pass" + i;
            accountRepository.save(new Account(username, password));
        }
        long repositorySizeAfterSave = accountRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            accountRepository.delete("user" + i);
        }

        long repositorySizeAfterDelete = accountRepository.count();
        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldDeleteAccountByEmployeeId() {
        Employee employee =
                Employee.builder("firstname", "lastname", LocalDate.now()).build();
        Account account = new Account(employee.getID(), "user", "pass");
        accountRepository.save(account);
        long repositorySizeAfterSave = accountRepository.count();

        accountRepository.delete(account.getEmployeeId());
        long repositorySizeAfterDelete = accountRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete + 1);
    }

    @Test
    void shouldDeleteMultipleAccountsByEmployeeId() {
        int counter = 10;
        String username;
        String password;
        Long employeeId;
        Employee[] employees = new Employee[counter];
        for(int i = 0; i < counter; i++) {
            employees[i] = Employee.builder(
                    "firstname" + i, "lastname" + i, LocalDate.now()).build();
        }
        for(int i = 0; i < counter; i++) {
            employeeId = employees[i].getID();
            username = "user" + i;
            password = "pass" + i;
            accountRepository.save(new Account(employeeId, username, password));
        }
        long repositorySizeAfterSave = accountRepository.count();

        for(int i = 0; i < counter; i++) {
            accountRepository.delete(employees[i].getID());
        }

        long repositorySizeAfterDelete = accountRepository.count();
        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Account account = new Account("user", "pass");
        accountRepository.save(account);
        Account foundAccount = accountRepository.findById(account.getUsername()).get();
        assertEquals(account, foundAccount);
    }

    @Test
    void shouldNotFindById() {
        Optional<Account> foundAccount = accountRepository.findById("USERNAME");
        assertEquals(Optional.empty(), foundAccount);
    }

    @Test
    void shouldFindByData() {
        Account account1 = new Account("user1", "pass");
        Account account2 = new Account("user2", "pass");
        accountRepository.save(account1);
        accountRepository.save(account2);
        List<Account> foundAccounts = accountRepository.findByData("pass", Account::getPassword).get();

        assertEquals(account1.getPassword(), foundAccounts.get(0).getPassword());
        assertEquals(account2.getPassword(), foundAccounts.get(1).getPassword());
    }

    @Test
    void shouldNotFindByData() {
        Optional<List<Account>> foundAccounts = accountRepository.findByData("This pass doesn't exists", Account::getPassword);
        assertEquals(Optional.empty(), foundAccounts);
    }

    @Test
    void shouldFindAll() {
        Account account1 = new Account("user1", "pass");
        Account account2 = new Account("user2", "pass");
        accountRepository.save(account1);
        accountRepository.save(account2);
        long repositorySizeAfterSave = accountRepository.count();

        List<Account> foundAccounts = accountRepository.findAll().get();
        long foundAccountsSize = foundAccounts.size();

        assertEquals(repositorySizeAfterSave, foundAccountsSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Account>> foundAccounts = accountRepository.findAll();
        assertEquals(Optional.empty(), foundAccounts);
    }
}
