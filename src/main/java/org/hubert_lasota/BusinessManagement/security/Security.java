package org.hubert_lasota.BusinessManagement.security;

import org.hubert_lasota.BusinessManagement.account.Account;

public interface Security {

    boolean authenticate(String username, String password);

    boolean isAuthorized(String role, Account account);
}
