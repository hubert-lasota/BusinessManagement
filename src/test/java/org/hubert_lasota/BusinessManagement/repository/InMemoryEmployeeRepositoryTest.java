package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.employee.Employee;
import org.hubert_lasota.BusinessManagement.entity.employee.EmployeeProfession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryEmployeeRepositoryTest {
    InMemoryEmployeeRepository inMemoryEmployeeRepository = InMemoryEmployeeRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = InMemoryEmployeeRepository.class.getDeclaredField("employees");
        field.setAccessible(true);
        Map<Long, Employee> employees = (Map<Long, Employee>) field.get(inMemoryEmployeeRepository);
        employees.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = inMemoryEmployeeRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        long counter = 100;
        Employee employee;
        for(int i = 0; i < counter; i++) {
            employee = new Employee("FirstName", "LastName", LocalDate.now());
            inMemoryEmployeeRepository.save(employee);
        }
        long repositoryCounter = inMemoryEmployeeRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveEmployee() {
        Employee actual = new Employee("FirstName", "LastName", LocalDate.now());
        long repositorySizeBeforeSave = inMemoryEmployeeRepository.count();

        Employee expected = inMemoryEmployeeRepository.save(actual);
        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();

        assertEquals(expected, actual);
        assertEquals(repositorySizeBeforeSave, repositorySizeAfterSave-1);
    }

    @Test
    void shouldSaveMultipleEmployees() {
        int counter = 10;
        String firstName;
        String lastName;
        Employee employee;

        for(int i = 0; i < counter; i++) {
            firstName = "FirstName" + i;
            lastName = "LastName" + i;
            employee = new Employee(firstName, lastName, LocalDate.now());
            inMemoryEmployeeRepository.save(employee);
        }

        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteEmployee() {
        Employee employee = new Employee("FirstName", "LastName", LocalDate.now());
        inMemoryEmployeeRepository.save(employee);
        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();

        inMemoryEmployeeRepository.delete(employee.getId());
        long repositorySizeAfterDelete = inMemoryEmployeeRepository.count();
        assertEquals(repositorySizeAfterSave, repositorySizeAfterDelete+1);
    }

    @Test
    void shouldDeleteMultipleEmployees() {
        int counter = 10;
        Employee[] employees = new Employee[counter];
        String firstName;
        String lastName;
        Employee employee;

        for(int i = 0; i < counter; i++) {
            firstName = "FirstName" + i;
            lastName = "LastName" + i;
            employee = new Employee(firstName, lastName, LocalDate.now());
            employees[i] = inMemoryEmployeeRepository.save(employee);
        }
        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            inMemoryEmployeeRepository.delete(employees[i].getId());
        }
        long repositorySizeAfterDelete = inMemoryEmployeeRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Employee employee = new Employee("FirstName", "LastName", LocalDate.now());
        inMemoryEmployeeRepository.save(employee);

        Employee foundEmployee = inMemoryEmployeeRepository.findById(employee.getId()).get();

        assertEquals(employee, foundEmployee);
    }

    @Test
    void shouldNotFindById() {
        Optional<Employee> foundEmployee = inMemoryEmployeeRepository.findById(-1L);
        assertEquals(Optional.empty(), foundEmployee);
    }

    @Test
    void shouldFindAll() {
        Employee employee1 = new Employee("FirstName", "LastName", LocalDate.now());
        Employee employee2 = new Employee("FirstName", "LastName", LocalDate.now());
        inMemoryEmployeeRepository.save(employee1);
        inMemoryEmployeeRepository.save(employee2);
        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();

        List<Employee> foundEmployees = inMemoryEmployeeRepository.findAll().get();
        long foundEmployeesSize = foundEmployees.size();

        assertEquals(repositorySizeAfterSave, foundEmployeesSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Employee>> foundEmployee = inMemoryEmployeeRepository.findAll();
        assertEquals(Optional.empty(), foundEmployee);
    }

    @Test
    void shouldFindByFirstName() {
        Employee employee1 = new Employee("FirstName", "LastName", LocalDate.now());
        Employee employee2 = new Employee("FirstName", "LastName", LocalDate.now());
        inMemoryEmployeeRepository.save(employee1);
        inMemoryEmployeeRepository.save(employee2);
        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();

        List<Employee> foundEmployees = inMemoryEmployeeRepository.findByFirstName("FirstName").get();
        long foundEmployeesSize = foundEmployees.size();

        assertEquals(repositorySizeAfterSave, foundEmployeesSize);
    }

    @Test
    void shouldNotFindByFirstName() {
        Optional<List<Employee>> foundEmployee = inMemoryEmployeeRepository.findByFirstName("firstName");
        assertEquals(Optional.empty(), foundEmployee);
    }

    @Test
    void shouldFindByLastName() {
        Employee employee1 = new Employee("FirstName", "LastName", LocalDate.now());
        Employee employee2 = new Employee("FirstName", "LastName", LocalDate.now());
        inMemoryEmployeeRepository.save(employee1);
        inMemoryEmployeeRepository.save(employee2);
        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();

        List<Employee> foundEmployees = inMemoryEmployeeRepository.findByLastName("LastName").get();
        long foundEmployeesSize = foundEmployees.size();

        assertEquals(repositorySizeAfterSave, foundEmployeesSize);
    }

    @Test
    void shouldNotFindByLastName() {
        Optional<List<Employee>> foundEmployee = inMemoryEmployeeRepository.findByLastName("lastName");
        assertEquals(Optional.empty(), foundEmployee);
    }

    @Test
    void shouldFindByYearOfBirth() {
        LocalDate localDate = LocalDate.of(1999, 1, 5);
        Employee employee1 = new Employee("FirstName", "LastName", localDate);
        Employee employee2 = new Employee("FirstName", "LastName", localDate);
        inMemoryEmployeeRepository.save(employee1);
        inMemoryEmployeeRepository.save(employee2);
        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();

        List<Employee> foundEmployees = inMemoryEmployeeRepository.findByYearOfBirth(Year.of(1999)).get();
        long foundEmployeesSize = foundEmployees.size();

        assertEquals(repositorySizeAfterSave, foundEmployeesSize);
    }

    @Test
    void shouldNotFindByYearOfBirth() {
        Optional<List<Employee>> foundEmployee = inMemoryEmployeeRepository.findByYearOfBirth(Year.of(1111));
        assertEquals(Optional.empty(), foundEmployee);
    }

    @Test
    void shouldFindByProfession() {
        Employee employee1 = new Employee("FirstName", "LastName", LocalDate.now());
        employee1.setProfession(EmployeeProfession.WAREHOUSE_WORKER);
        Employee employee2 = new Employee("FirstName", "LastName", LocalDate.now());
        employee2.setProfession(EmployeeProfession.WAREHOUSE_WORKER);
        inMemoryEmployeeRepository.save(employee1);
        inMemoryEmployeeRepository.save(employee2);
        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();

        List<Employee> foundEmployees = inMemoryEmployeeRepository.findByProfession(EmployeeProfession.WAREHOUSE_WORKER).get();
        long foundEmployeesSize = foundEmployees.size();

        assertEquals(repositorySizeAfterSave, foundEmployeesSize);
    }

    @Test
    void shouldNotFindByProfession() {
        Optional<List<Employee>> foundEmployee = inMemoryEmployeeRepository.findByProfession(EmployeeProfession.NONE);
        assertEquals(Optional.empty(), foundEmployee);
    }

    @Test
    void shouldFindBetweenSalaries() {
        Employee employee1 = new Employee("FirstName", "LastName", LocalDate.now());
        BigDecimal salary1 = new BigDecimal("4500");
        employee1.setSalary(salary1);

        Employee employee2 = new Employee("FirstName", "LastName", LocalDate.now());
        BigDecimal salary2 = new BigDecimal("6500");
        employee2.setSalary(salary2);

        inMemoryEmployeeRepository.save(employee1);
        inMemoryEmployeeRepository.save(employee2);

        long repositorySizeAfterSave = inMemoryEmployeeRepository.count();
        List<Employee> foundEmployees = inMemoryEmployeeRepository.findBetweenSalaries(salary1, salary2).get();
        long foundEmployeesSize = foundEmployees.size();

        assertEquals(repositorySizeAfterSave, foundEmployeesSize);
    }

    @Test
    void shouldNotFindBetweenSalaries() {
        Optional<List<Employee>> foundEmployee = inMemoryEmployeeRepository.findBetweenSalaries(BigDecimal.ZERO, BigDecimal.ONE);
        assertEquals(Optional.empty(), foundEmployee);
    }

}
