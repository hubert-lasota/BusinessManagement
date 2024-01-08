package org.hubert_lasota.BusinessManagement.security;

import org.hubert_lasota.BusinessManagement.entity.user.User;

/**
 * The {@code Security} interface defines methods for user authentication and authorization.
 * Implementations of this interface handle user authentication based on a username and password
 * and check if a user is authorized based on their role.
 */
public interface Security {

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username of the user attempting to authenticate.
     * @param password The password associated with the given username.
     * @return {@code true} if the authentication is successful, {@code false} otherwise.
     */
    boolean authenticate(String username, String password);

    /**
     * Checks if a user has the specified role.
     *
     * @param role The role to check.
     * @param user The user for whom the role is being checked.
     * @return {@code true} if the user is authorized with the specified role, {@code false} otherwise.
     */
    boolean isAuthorized(String role, User user);

}
