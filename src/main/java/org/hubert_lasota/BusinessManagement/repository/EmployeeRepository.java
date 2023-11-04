package org.hubert_lasota.BusinessManagement.repository;


import org.hubert_lasota.BusinessManagement.employee.Employee;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class EmployeeRepository  implements Repository<Employee> {
    private static EmployeeRepository employeeRepository;
    private Map<Long, Employee> employees;

    private EmployeeRepository() {
        employees = new HashMap<>();
    }

    public static EmployeeRepository getInstance() {
        if(employeeRepository == null) {
            employeeRepository = new EmployeeRepository();
        }
        return employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employees.putIfAbsent(employee.getID(), employee);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    public Optional<List<Employee>> findByData(String data, Function<Employee, String> fieldExtractor) {
        List<Employee> tempList =  employees.values().stream()
                .filter(e -> fieldExtractor.apply(e).contains(data))
                .collect(Collectors.toList());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public Optional<List<Employee>> findAll() {
        List<Employee> tempList = List.copyOf(employees.values());
        return tempList.isEmpty() ? Optional.empty() : Optional.of(tempList);
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee tempEmployee = findById(id).orElseThrow(NoSuchIdException::new);
        return update(tempEmployee, employee);
    }

    private Employee update(Employee employeeToUpdate, Employee employeeUpdater) {
        employeeToUpdate.setFirstName(employeeUpdater.getFirstName());
        employeeToUpdate.setLastName(employeeUpdater.getLastName());
        employeeToUpdate.setDateOfBirth(employeeUpdater.getDateOfBirth());
        employeeToUpdate.setAddress(employeeUpdater.getAddress());
        employeeToUpdate.setSalary(employeeUpdater.getSalary());
        employeeToUpdate.setProfession(employeeUpdater.getProfession());
        return employeeToUpdate;
    }

    @Override
    public void delete(Long id) {
        Employee employee = findById(id).orElseThrow(NoSuchIdException::new);
        employees.remove(employee.getID(), employee);
    }

    @Override
    public Long count() {
        return (long) employees.size();
    }
}
