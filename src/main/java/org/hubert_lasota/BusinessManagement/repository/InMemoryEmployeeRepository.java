package org.hubert_lasota.BusinessManagement.repository;


import org.hubert_lasota.BusinessManagement.entity.employee.Employee;
import org.hubert_lasota.BusinessManagement.entity.employee.EmployeeProfession;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.math.BigDecimal;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public class InMemoryEmployeeRepository implements EmployeeRepository {
    private static InMemoryEmployeeRepository inMemoryEmployeeRepository;
    private final Map<Long, Employee> employees;

    private InMemoryEmployeeRepository() {
        employees = new HashMap<>();
    }

    public static InMemoryEmployeeRepository getInstance() {
        if(inMemoryEmployeeRepository == null) {
            inMemoryEmployeeRepository = new InMemoryEmployeeRepository();
        }
        return inMemoryEmployeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        employees.putIfAbsent(employee.getId(), employee);
        return employee;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Optional<List<Employee>> findByFirstName(String firstName) {
        return findByData(firstName, Employee::getFirstName);
    }

    @Override
    public Optional<List<Employee>> findByLastName(String lastName) {
        return findByData(lastName, Employee::getLastName);
    }

    private Optional<List<Employee>> findByData(String data, Function<Employee, String> fieldExtractor) {
        List<Employee> foundEmployees =  employees.values().stream()
                .filter(e -> fieldExtractor.apply(e).contains(data))
                .collect(Collectors.toList());

        return foundEmployees.isEmpty() ? Optional.empty() : Optional.of(foundEmployees);
    }

    @Override
    public Optional<List<Employee>> findByYearOfBirth(Year yearOfBirth) {
        List<Employee> foundEmployees =  employees.values().stream()
                .filter(e -> e.getDateOfBirth().getYear() == yearOfBirth.getValue())
                .collect(Collectors.toList());

        return foundEmployees.isEmpty() ? Optional.empty() : Optional.of(foundEmployees);
    }

    @Override
    public Optional<List<Employee>> findBetweenSalaries(BigDecimal lowerSalary, BigDecimal upperSalary) {
        return findEmployeesBetweenComparableData(lowerSalary, upperSalary, Employee::getSalary);
    }

    private <T extends Comparable<T>> Optional<List<Employee>> findEmployeesBetweenComparableData(
            T start, T end, Function<Employee, T> fieldExtractor) {
        List<Employee> employeesBetweenData = employees.values()
                .stream()
                .filter(p -> fieldExtractor.apply(p).compareTo(start) >= 0)
                .filter(p -> fieldExtractor.apply(p).compareTo(end) <= 0)
                .collect(Collectors.toList());

        return employeesBetweenData.isEmpty() ? Optional.empty() : Optional.of(employeesBetweenData);
    }

    @Override
    public Optional<List<Employee>> findByProfession(EmployeeProfession employeeProfession) {
        List<Employee> foundEmployees =  employees.values().stream()
                .filter(e -> e.getProfession().equals(employeeProfession))
                .collect(Collectors.toList());

        return foundEmployees.isEmpty() ? Optional.empty() : Optional.of(foundEmployees);
    }

    @Override
    public Optional<List<Employee>> findAll() {
        List<Employee> foundEmployees = List.copyOf(employees.values());
        return foundEmployees.isEmpty() ? Optional.empty() : Optional.of(foundEmployees);
    }

    @Override
    public void delete(Long id) {
        Employee employee = findById(id).orElseThrow(NoSuchIdException::new);
        employees.remove(id, employee);
    }

    @Override
    public Long count() {
        return (long) employees.size();
    }

}
