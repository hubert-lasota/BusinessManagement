package org.hubert_lasota.BusinessManagement.repository;


import org.hubert_lasota.BusinessManagement.employee.Employee;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class EmployeeRepository  implements Repository<Employee, Long> {
    private static EmployeeRepository employeeRepository;
    private final Map<Long, Employee> employees;

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
        employees.putIfAbsent(employee.getID(), employee);
        return employee;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
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
    public void delete(Long id) {
        Employee employee = findById(id).orElseThrow(NoSuchIdException::new);
        employees.remove(employee.getID(), employee);
    }

    @Override
    public Long count() {
        return (long) employees.size();
    }
}
