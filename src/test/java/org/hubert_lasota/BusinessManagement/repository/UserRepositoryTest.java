package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.user.User;
import org.hubert_lasota.BusinessManagement.employee.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {
    UserRepository userRepository = UserRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = UserRepository.class.getDeclaredField("users");
        field.setAccessible(true);
        Map<String, User> accounts = (Map<String, User>) field.get(userRepository);
        accounts.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = userRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        long counter = 100;
        String username;
        String password;
        for(int i = 0; i < counter; i++) {
            username = "user" + i;
            password = "pass" + i;
            userRepository.save(new User(username, password));
        }
        long repositoryCounter = userRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveAccount() {
        User actual = new User("user", "pass");
        long repositorySizeBeforeSave = userRepository.count();

        User expected = userRepository.save(actual);
        long repositorySizeAfterSave = userRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleAccounts() {
        int counter = 10;
        String username;
        String password;
        for(int i = 0; i < counter; i++) {
            username = "user" + i;
            password = "pass" + i;
            userRepository.save(new User(username, password));
        }
        long repositorySizeAfterSave = userRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteAccountByUsername() {
        User user = new User("user", "pass");
        userRepository.save(user);
        long repositorySizeAfterSave = userRepository.count();

        userRepository.delete(user.getUsername());
        long repositorySizeAfterDelete = userRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete + 1);
    }

    @Test
    void shouldDeleteMultipleAccountsByUsername() {
        int counter = 10;
        String username;
        String password;
        for(int i = 0; i < counter; i++) {
            username = "user" + i;
            password = "pass" + i;
            userRepository.save(new User(username, password));
        }
        long repositorySizeAfterSave = userRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            userRepository.delete("user" + i);
        }

        long repositorySizeAfterDelete = userRepository.count();
        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldDeleteAccountByEmployeeId() {
        Employee employee =
                Employee.builder("firstname", "lastname", LocalDate.now()).build();
        User user = new User(employee.getID(), "user", "pass");
        userRepository.save(user);
        long repositorySizeAfterSave = userRepository.count();

        userRepository.delete(user.getEmployeeId());
        long repositorySizeAfterDelete = userRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete + 1);
    }

    @Test
    void shouldDeleteMultipleAccountsByEmployeeId() {
        int counter = 10;
        String username;
        String password;
        Long employeeId;
        Employee[] employees = new Employee[counter];
        for(int i = 0; i < counter; i++) {
            employees[i] = Employee.builder(
                    "firstname" + i, "lastname" + i, LocalDate.now()).build();
        }
        for(int i = 0; i < counter; i++) {
            employeeId = employees[i].getID();
            username = "user" + i;
            password = "pass" + i;
            userRepository.save(new User(employeeId, username, password));
        }
        long repositorySizeAfterSave = userRepository.count();

        for(int i = 0; i < counter; i++) {
            userRepository.delete(employees[i].getID());
        }

        long repositorySizeAfterDelete = userRepository.count();
        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        User user = new User("user", "pass");
        userRepository.save(user);
        User foundUser = userRepository.findById(user.getUsername()).get();
        assertEquals(user, foundUser);
    }

    @Test
    void shouldNotFindById() {
        Optional<User> foundAccount = userRepository.findById("USERNAME");
        assertEquals(Optional.empty(), foundAccount);
    }

    @Test
    void shouldFindByData() {
        User user1 = new User("user1", "pass");
        User user2 = new User("user2", "pass");
        userRepository.save(user1);
        userRepository.save(user2);
        List<User> foundUsers = userRepository.findByData("pass", User::getPassword).get();

        assertEquals(user1.getPassword(), foundUsers.get(0).getPassword());
        assertEquals(user2.getPassword(), foundUsers.get(1).getPassword());
    }

    @Test
    void shouldNotFindByData() {
        Optional<List<User>> foundAccounts = userRepository.findByData("This pass doesn't exists", User::getPassword);
        assertEquals(Optional.empty(), foundAccounts);
    }

    @Test
    void shouldFindAll() {
        User user1 = new User("user1", "pass");
        User user2 = new User("user2", "pass");
        userRepository.save(user1);
        userRepository.save(user2);
        long repositorySizeAfterSave = userRepository.count();

        List<User> foundUsers = userRepository.findAll().get();
        long foundAccountsSize = foundUsers.size();

        assertEquals(repositorySizeAfterSave, foundAccountsSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<User>> foundAccounts = userRepository.findAll();
        assertEquals(Optional.empty(), foundAccounts);
    }
}
