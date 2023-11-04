package org.hubert_lasota.BusinessManagement.security;

public interface Security {

    boolean authenticate(String username, String password);

    boolean isAuthorized(String role, Account account);
}
