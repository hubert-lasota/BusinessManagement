package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.user.User;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class InMemoryUserRepository implements UserRepository {
    private static InMemoryUserRepository inMemoryUserRepository;
    private final Map<String, User> users;

    private InMemoryUserRepository() {
        users = new HashMap<>();
    }

    public static InMemoryUserRepository getInstance() {
        if(inMemoryUserRepository == null) {
            inMemoryUserRepository = new InMemoryUserRepository();
        }
        return inMemoryUserRepository;
    }

    @Override
    public User save(User user) {
        users.putIfAbsent(user.getUsername(), user);
        return user;
    }

    @Override
    public Optional<User> findByEmployeeId(Long employeeId) {
        return users.values().stream()
                .filter(a -> a.getEmployeeId().equals(employeeId))
                .findFirst();
    }

    @Override
    public Optional<User> findById(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public Optional<List<User>> findByPassword(String password) {
        List<User> foundUsers = users.values().stream()
                .filter(u -> u.getPassword().equals(password))
                .collect(Collectors.toList());

        return foundUsers.isEmpty() ? Optional.empty() : Optional.of(foundUsers);
    }

    @Override
    public Optional<List<User>> findByRole(String role) {
        List<User> foundUsers = users.values().stream()
                .filter(u -> u.getRoles().contains(role))
                .collect(Collectors.toList());

        return foundUsers.isEmpty() ? Optional.empty() : Optional.of(foundUsers);
    }

    @Override
    public Optional<List<User>> findAll() {
        List<User> foundUsers = List.copyOf(users.values());
        return foundUsers.isEmpty() ? Optional.empty() : Optional.of(foundUsers);
    }

    public void delete(Long employeeId) {
        User user = findByEmployeeId(employeeId).orElseThrow(NoSuchIdException::new);
        users.remove(user.getUsername(), user);
    }

    @Override
    public void delete(String username) {
        users.remove(username);
    }

    @Override
    public Long count() {
        return (long) users.size();
    }

}
