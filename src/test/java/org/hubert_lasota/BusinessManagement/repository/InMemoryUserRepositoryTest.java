package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.employee.Employee;
import org.hubert_lasota.BusinessManagement.entity.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryUserRepositoryTest {
    InMemoryUserRepository inMemoryUserRepository = InMemoryUserRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = InMemoryUserRepository.class.getDeclaredField("users");
        field.setAccessible(true);
        Map<String, User> accounts = (Map<String, User>) field.get(inMemoryUserRepository);
        accounts.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = inMemoryUserRepository.count();
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
            inMemoryUserRepository.save(new User(username, password));
        }
        long repositoryCounter = inMemoryUserRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveAccount() {
        User actual = new User("user", "pass");
        long repositorySizeBeforeSave = inMemoryUserRepository.count();

        User expected = inMemoryUserRepository.save(actual);
        long repositorySizeAfterSave = inMemoryUserRepository.count();

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
            inMemoryUserRepository.save(new User(username, password));
        }
        long repositorySizeAfterSave = inMemoryUserRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteAccountByUsername() {
        User user = new User("user", "pass");
        inMemoryUserRepository.save(user);
        long repositorySizeAfterSave = inMemoryUserRepository.count();

        inMemoryUserRepository.delete(user.getUsername());
        long repositorySizeAfterDelete = inMemoryUserRepository.count();
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
            inMemoryUserRepository.save(new User(username, password));
        }
        long repositorySizeAfterSave = inMemoryUserRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            inMemoryUserRepository.delete("user" + i);
        }

        long repositorySizeAfterDelete = inMemoryUserRepository.count();
        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldDeleteAccountByEmployeeId() {
        Employee employee =
                new Employee("firstname", "lastname", LocalDate.now());
        User user = new User(employee.getId(), "user", "pass");
        inMemoryUserRepository.save(user);
        long repositorySizeAfterSave = inMemoryUserRepository.count();

        inMemoryUserRepository.delete(user.getEmployeeId());
        long repositorySizeAfterDelete = inMemoryUserRepository.count();
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
            employees[i] = new Employee("firstname" + i, "lastname" + i, LocalDate.now());
        }
        for(int i = 0; i < counter; i++) {
            employeeId = employees[i].getId();
            username = "user" + i;
            password = "pass" + i;
            inMemoryUserRepository.save(new User(employeeId, username, password));
        }
        long repositorySizeAfterSave = inMemoryUserRepository.count();

        for(int i = 0; i < counter; i++) {
            inMemoryUserRepository.delete(employees[i].getId());
        }

        long repositorySizeAfterDelete = inMemoryUserRepository.count();
        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        User user = new User("user", "pass");
        inMemoryUserRepository.save(user);
        User foundUser = inMemoryUserRepository.findById(user.getUsername()).get();
        assertEquals(user, foundUser);
    }

    @Test
    void shouldNotFindById() {
        Optional<User> foundAccount = inMemoryUserRepository.findById("USERNAME");
        assertEquals(Optional.empty(), foundAccount);
    }

    @Test
    void shouldFindAll() {
        User user1 = new User("user1", "pass");
        User user2 = new User("user2", "pass");
        inMemoryUserRepository.save(user1);
        inMemoryUserRepository.save(user2);
        long repositorySizeAfterSave = inMemoryUserRepository.count();

        List<User> foundUsers = inMemoryUserRepository.findAll().get();
        long foundAccountsSize = foundUsers.size();

        assertEquals(repositorySizeAfterSave, foundAccountsSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<User>> foundAccounts = inMemoryUserRepository.findAll();
        assertEquals(Optional.empty(), foundAccounts);
    }

    @Test
    void shouldFindByEmployeeId() {
        User user = new User(0L,"user", "pass");
        inMemoryUserRepository.save(user);
        User foundUser = inMemoryUserRepository.findByEmployeeId(user.getEmployeeId()).get();
        assertEquals(user, foundUser);
    }

    @Test
    void shouldNotFindByEmployeeId() {
        Optional<User> foundAccount = inMemoryUserRepository.findByEmployeeId(-1L);
        assertEquals(Optional.empty(), foundAccount);
    }

    @Test
    void shouldFindByPassword() {
        User user1 = new User("user1", "pass");
        User user2 = new User("user2", "pass");
        inMemoryUserRepository.save(user1);
        inMemoryUserRepository.save(user2);
        long repositorySizeAfterSave = inMemoryUserRepository.count();

        List<User> foundUsers = inMemoryUserRepository.findByPassword("pass").get();
        long foundAccountsSize = foundUsers.size();

        assertEquals(repositorySizeAfterSave, foundAccountsSize);
    }

    @Test
    void shouldNotFindByPassword() {
        Optional<List<User>> foundAccounts = inMemoryUserRepository.findByPassword("emptyPass");
        assertEquals(Optional.empty(), foundAccounts);
    }

    @Test
    void shouldFindByRole() {
        User user1 = new User("user1", "pass", "WAREHOUSE_EMPLOYEE");
        User user2 = new User("user2", "pass", "WAREHOUSE_EMPLOYEE");
        inMemoryUserRepository.save(user1);
        inMemoryUserRepository.save(user2);
        long repositorySizeAfterSave = inMemoryUserRepository.count();

        List<User> foundUsers = inMemoryUserRepository.findByRole("WAREHOUSE_EMPLOYEE").get();
        long foundAccountsSize = foundUsers.size();

        assertEquals(repositorySizeAfterSave, foundAccountsSize);
    }

    @Test
    void shouldNotFindByRole() {
        Optional<List<User>> foundAccounts = inMemoryUserRepository.findByPassword("emptyRole");
        assertEquals(Optional.empty(), foundAccounts);
    }

}
