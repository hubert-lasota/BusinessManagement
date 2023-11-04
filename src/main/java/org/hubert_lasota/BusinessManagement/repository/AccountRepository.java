package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.security.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public class AccountRepository implements Repository<Account> {
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

    @Override
    public Account save(Account account) {
        return accounts.putIfAbsent(account.getUsername(), account);
    }

    @Override
    public Optional<Account> findById(Long employeeId) {
        return accounts.values().stream()
                .filter(a -> a.getEmployeeId().equals(employeeId))
                .findFirst();
    }

    public Account findByUsername(String username) {
        return accounts.get(username);
    }

    public List<Account> findByData(String data, Function<Account, String> fieldExtractor) {
        return accounts.values().stream()
                .filter(a -> fieldExtractor.apply(a).contains(data))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<List<Account>> findAll() {
        List<Account> tempList = List.copyOf(accounts.values());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public Account update(Long employeeId, Account account) {
        Account tempAccount = findById(employeeId).orElseThrow(NoSuchIdException::new);
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

    @Override
    public void delete(Long employeeId) {
        Account account = findById(employeeId).orElseThrow(NoSuchIdException::new);
        accounts.remove(account.getUsername(), account);
    }

    @Override
    public Long count() {
        return (long) accounts.size();
    }
}
