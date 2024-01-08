package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.employee.Employee;
import org.hubert_lasota.BusinessManagement.entity.employee.EmployeeProfession;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    Employee findEmployeeById(Long id);

    List<Employee> findEmployeesByFirstName(String firstName);

    List<Employee> findEmployeesByLastName(String lastName);

    List<Employee> findEmployeesByYearOfBirth(Year dateOfBirth);

    List<Employee> findEmployeesBetweenSalaries(BigDecimal lowerSalary, BigDecimal upperSalary);

    List<Employee> findEmployeesByProfession(EmployeeProfession employeeProfession);

    List<Employee> findAllEmployees();

    void deleteEmployee(Long id);

}
