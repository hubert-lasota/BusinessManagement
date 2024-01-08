package org.hubert_lasota.BusinessManagement.security;

import org.hubert_lasota.BusinessManagement.entity.user.User;
import org.hubert_lasota.BusinessManagement.service.UserService;

public class SecurityManager implements Security {
    private final UserService userService;

    public SecurityManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = userService.findUserById(username);
        return user.getPassword().equals(password);
    }

    @Override
    public boolean isAuthorized(String role, User user) {
        return user.getRoles().stream()
                .anyMatch(r -> r.equals(role));
    }

}
