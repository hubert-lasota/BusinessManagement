package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public class UserRepository implements Repository<User, String> {
    private static UserRepository userRepository;
    private final Map<String, User> users;

    private UserRepository() {
        users = new HashMap<>();
    }

    public static UserRepository getInstance() {
        if(userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    @Override
    public User save(User user) {
        users.putIfAbsent(user.getUsername(), user);
        return user;
    }

    public Optional<User> findByEmployeeId(Long employeeId) {
        return users.values().stream()
                .filter(a -> a.getEmployeeId().equals(employeeId))
                .findFirst();
    }

    @Override
    public Optional<User> findById(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public Optional<List<User>> findByData(String data, Function<User, String> fieldExtractor) {
        List<User> tempList = users.values().stream()
                .filter(a -> fieldExtractor.apply(a).contains(data))
                .collect(Collectors.toList());

        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public Optional<List<User>> findAll() {
        List<User> tempList = List.copyOf(users.values());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
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
