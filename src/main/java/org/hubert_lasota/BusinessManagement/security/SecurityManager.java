package org.hubert_lasota.BusinessManagement.security;

import org.hubert_lasota.BusinessManagement.account.Account;
import org.hubert_lasota.BusinessManagement.repository.AccountRepository;

import java.util.List;

public class SecurityManager implements Security {
    private static SecurityManager securityManager;
    private AccountRepository accountRepository;

    private SecurityManager() {
        accountRepository = AccountRepository.getInstance();
    }

    public static SecurityManager getInstance() {
        if(securityManager == null) {
            securityManager = new SecurityManager();
        }
        return securityManager;
    }


    @Override
    public boolean authenticate(String username, String password) {
        List<Account> tempList = accountRepository.findByData(username, Account::getUsername);
        for(Account tempAccount : tempList) {
            if(tempAccount.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAuthorized(String role, Account account) {
        return account.getRoles().stream()
                .anyMatch(r -> r.equals(role));
    }

}
