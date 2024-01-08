package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.user.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User findUserById(String username);

    User findUserByEmployeeId(Long employeeId);

    List<User> findUsersByPassword(String password);

    List<User> findUsersByRole(String role);

    List<User> findAllUsers();

    boolean updateUsername(String username, String newUsername);

    void updatePassword(String username, String password);
    void deleteUserById(String username);

}
