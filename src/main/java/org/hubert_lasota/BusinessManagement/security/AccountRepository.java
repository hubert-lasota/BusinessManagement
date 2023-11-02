package org.hubert_lasota.BusinessManagement.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class AccountRepository {
    private static AccountRepository accountRepository;
    private Map<String, Account> accounts;

    private AccountRepository() {
        accounts = new HashMap<>();
    }

    public static AccountRepository getInstance() {
        if(accountRepository == null) {
            accountRepository = new AccountRepository();
        }
        return accountRepository;
    }

    public Account save(Account account) {
        return accounts.putIfAbsent(account.getUsername(), account);
    }


    public Account findById(Long employeeId) {
        return accounts.values().stream()
                .filter(a -> a.getEmployeeId().equals(employeeId))
                .findFirst()
                .get();
    }

    public Account findByUsername(String username) {
        return accounts.get(username);
    }

    public List<Account> findByData(String data, Function<Account, String> fieldExtractor) {
        return accounts.values().stream()
                .filter(a -> fieldExtractor.apply(a).contains(data))
                .collect(Collectors.toList());
    }

    public List<Account> findAll() {
        return (List<Account>) accounts.values();
    }

    public Account update(Long employeeId, Account account) {
        Account tempAccount = findById(employeeId);
        return update(tempAccount, account);
    }

    public Account update(String username, Account account) {
        Account tempAccount = findByUsername(username);
        return update(tempAccount, account);
    }

    private Account update(Account accountToUpdate, Account accountUpdater) {
        accountToUpdate.setUsername(accountUpdater.getUsername());
        accountToUpdate.setPassword(accountUpdater.getPassword());
        accountToUpdate.setRoles(accountUpdater.getRoles());
        return accountToUpdate;
    }

    public void delete(Long employeeId) {
        Account account = findById(employeeId);
        accounts.remove(account.getUsername(), account);
    }
}
