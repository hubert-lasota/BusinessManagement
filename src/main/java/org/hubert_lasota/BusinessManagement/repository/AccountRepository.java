package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.account.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public class AccountRepository implements Repository<Account, String> {
    private static AccountRepository accountRepository;
    private final Map<String, Account> accounts;

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
        accounts.putIfAbsent(account.getUsername(), account);
        return account;
    }

    public Optional<Account> findByEmployeeId(Long employeeId) {
        return accounts.values().stream()
                .filter(a -> a.getEmployeeId().equals(employeeId))
                .findFirst();
    }

    @Override
    public Optional<Account> findById(String username) {
        return Optional.ofNullable(accounts.get(username));
    }

    public Optional<List<Account>> findByData(String data, Function<Account, String> fieldExtractor) {
        List<Account> tempList = accounts.values().stream()
                .filter(a -> fieldExtractor.apply(a).contains(data))
                .collect(Collectors.toList());

        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public Optional<List<Account>> findAll() {
        List<Account> tempList = List.copyOf(accounts.values());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    public void delete(Long employeeId) {
        Account account = findByEmployeeId(employeeId).orElseThrow(NoSuchIdException::new);
        accounts.remove(account.getUsername(), account);
    }

    @Override
    public void delete(String username) {
        accounts.remove(username);
    }

    @Override
    public Long count() {
        return (long) accounts.size();
    }
}
