package org.hubert_lasota.BusinessManagement.employee;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class EmployeeRepository {
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

    public Employee save(Employee employee) {
        return employees.putIfAbsent(employee.getID(), employee);
    }

    public List<Employee> save(Employee... employees) {
        List<Employee> tempList = new ArrayList<>();
        for(Employee employee : employees) {
            if(!(this.employees.containsKey(employee.getID()))) {
                save(employee);
                tempList.add(employee);
            }
        }
        return tempList;
    }

    public Employee findById(Long id) {
        return employees.get(id);
    }

    public List<Employee> findByData(String data, Function<Employee, String> fieldExtractor) {
        return employees.values().stream()
                .filter(e -> fieldExtractor.apply(e).contains(data))
                .collect(Collectors.toList());
    }

    public List<Employee> findAll() {
        return (List<Employee>) employees.values();
    }

    public Employee update(Long id, Employee employee) {
        Employee tempEmployee = findById(id);
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

    public void delete(Long id) {
        Employee employee = findById(id);
        employees.remove(employee.getID(), employee);
    }
}
