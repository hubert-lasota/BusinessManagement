package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.user.User;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.exception.NoUsersInDatabaseException;
import org.hubert_lasota.BusinessManagement.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User saveUser(User user) {
        if(isUserPresentInDatabase(user.getUsername())) {
            return user;
        }
        return userRepository.save(user);
    }

    private boolean isUserPresentInDatabase(String username) {
        return userRepository.findById(username).isPresent();
    }

    @Override
    public User findUserById(String username) {
        return userRepository.findById(username)
                .orElseThrow(NoSuchIdException::new);
    }

    @Override
    public User findUserByEmployeeId(Long employeeId) {
        return userRepository.findByEmployeeId(employeeId)
                .orElseThrow(NoSuchIdException::new);
    }

    @Override
    public List<User> findUsersByPassword(String password) {
        return userRepository.findByPassword(password)
                .orElseThrow(() -> new NoUsersInDatabaseException("There are no users with this password in database"));
    }

    @Override
    public List<User> findUsersByRole(String role) {
        return userRepository.findByRole(role)
                .orElseThrow(() -> new NoUsersInDatabaseException("There are no users with this role in database"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll().orElseThrow(NoUsersInDatabaseException::new);
    }

    @Override
    public boolean updateUsername(String username, String newUsername) {
        User userToUpdate = findUserById(username);
        if(isUserPresentInDatabase(newUsername)) {
            return false;
        }

        userToUpdate.setUsername(newUsername);
        return true;
    }

    @Override
    public void updatePassword(String username, String password) {
        User userToUpdate = findUserById(username);
        userToUpdate.setPassword(password);
    }

    @Override
    public void deleteUserById(String username) {
        userRepository.delete(username);
    }

}
