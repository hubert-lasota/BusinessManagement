package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.employee.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeRepositoryTest {
    EmployeeRepository employeeRepository = EmployeeRepository.getInstance();

    @AfterEach
    void tearDown() throws Exception {
        Field field = EmployeeRepository.class.getDeclaredField("employees");
        field.setAccessible(true);
        Map<Long, Employee> employees = (Map<Long, Employee>) field.get(employeeRepository);
        employees.clear();
    }

    @Test
    void shouldCountZero() {
        Long actual = employeeRepository.count();
        assertEquals(0L, actual);
    }

    @Test
    void shouldCount() {
        long counter = 100;
        Employee employee;
        for(int i = 0; i < counter; i++) {
            employee = Employee.builder("FirstName", "LastName", LocalDate.now()).build();
            employeeRepository.save(employee);
        }
        long repositoryCounter = employeeRepository.count();
        assertEquals(counter, repositoryCounter);
    }

    @Test
    void shouldSaveEmployee() {
        Employee actual = Employee.builder("FirstName", "LastName", LocalDate.now()).build();
        long repositorySizeBeforeSave = employeeRepository.count();

        Employee expected = employeeRepository.save(actual);
        long repositorySizeAfterSave = employeeRepository.count();

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
            employee = Employee.builder(firstName, lastName, LocalDate.now()).build();
            employeeRepository.save(employee);
        }

        long repositorySizeAfterSave = employeeRepository.count();
        assertEquals(counter, repositorySizeAfterSave);
    }

    @Test
    void shouldDeleteEmployee() {
        Employee employee = Employee.builder("FirstName", "LastName", LocalDate.now()).build();
        employeeRepository.save(employee);
        long repositorySizeAfterSave = employeeRepository.count();

        employeeRepository.delete(employee.getID());
        long repositorySizeAfterDelete = employeeRepository.count();
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
            employee = Employee.builder(firstName, lastName, LocalDate.now()).build();
            employees[i] = employeeRepository.save(employee);
        }
        long repositorySizeAfterSave = employeeRepository.count();

        for(int i = 0; i < repositorySizeAfterSave; i++) {
            employeeRepository.delete(employees[i].getID());
        }
        long repositorySizeAfterDelete = employeeRepository.count();

        long actualRepositorySize = repositorySizeAfterDelete + repositorySizeAfterSave;
        assertEquals(repositorySizeAfterSave, actualRepositorySize);
    }

    @Test
    void shouldFindById() {
        Employee employee = Employee.builder("FirstName", "LastName", LocalDate.now()).build();
        employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository.findById(employee.getID()).get();

        assertEquals(employee, foundEmployee);
    }

    @Test
    void shouldNotFindById() {
        Optional<Employee> foundEmployee = employeeRepository.findById(-1L);
        assertEquals(Optional.empty(), foundEmployee);
    }

    @Test
    void shouldFindByData() {
        Employee employee1 = Employee.builder("FirstName", "LastName", LocalDate.now()).build();
        Employee employee2 = Employee.builder("FirstName", "LastName", LocalDate.now()).build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        List<Employee> foundEmployees = employeeRepository.findByData("FirstName", Employee::getFirstName).get();

        assertEquals(employee1, foundEmployees.get(0));
        assertEquals(employee2, foundEmployees.get(1));
    }

    @Test
    void shouldNotFindByData() {
        Optional<List<Employee>> foundEmployees = employeeRepository.findByData("This data doesn't exists", Employee::getFirstName);
        assertEquals(Optional.empty(), foundEmployees);
    }

    @Test
    void shouldFindAll() {
        Employee employee1 = Employee.builder("FirstName", "LastName", LocalDate.now()).build();
        Employee employee2 = Employee.builder("FirstName", "LastName", LocalDate.now()).build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        long repositorySizeAfterSave = employeeRepository.count();

        List<Employee> foundEmployees = employeeRepository.findAll().get();
        long foundEmployeesSize = foundEmployees.size();

        assertEquals(repositorySizeAfterSave, foundEmployeesSize);
    }

    @Test
    void shouldNotFindAll() {
        Optional<List<Employee>> foundEmployee = employeeRepository.findAll();
        assertEquals(Optional.empty(), foundEmployee);
    }
}
