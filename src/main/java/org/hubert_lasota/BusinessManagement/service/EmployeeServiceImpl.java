package org.hubert_lasota.BusinessManagement.service;

import org.hubert_lasota.BusinessManagement.entity.address.EmployeeAddress;
import org.hubert_lasota.BusinessManagement.entity.employee.Employee;
import org.hubert_lasota.BusinessManagement.entity.employee.EmployeeProfession;
import org.hubert_lasota.BusinessManagement.exception.NoEmployeesInDatabaseException;
import org.hubert_lasota.BusinessManagement.exception.NoSuchIdException;
import org.hubert_lasota.BusinessManagement.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public Employee saveEmployee(Employee employee) {
        if(isEmployeePresentInDatabase(employee.getId())) {
            return employee;
        }
        EmployeeAddress.addRelation(employee.getAddressId(), employee.getId());
        return employeeRepository.save(employee);
    }

    private boolean isEmployeePresentInDatabase(Long id) {
        return employeeRepository.findById(id).isPresent();
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(NoSuchIdException::new);
    }

    @Override
    public List<Employee> findEmployeesByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName)
                .orElseThrow(() -> new NoEmployeesInDatabaseException("There are no employees with this first name in database"));
    }

    @Override
    public List<Employee> findEmployeesByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName)
                .orElseThrow(() -> new NoEmployeesInDatabaseException("There are no employees with this last name in database"));
    }

    @Override
    public List<Employee> findEmployeesByYearOfBirth(Year dateOfBirth) {
        return employeeRepository.findByYearOfBirth(dateOfBirth)
                .orElseThrow(() -> new NoEmployeesInDatabaseException("There are no employees with this year of birth name in database"));
    }

    @Override
    public List<Employee> findEmployeesBetweenSalaries(BigDecimal lowerSalary, BigDecimal upperSalary) {
        return employeeRepository.findBetweenSalaries(lowerSalary, upperSalary)
                .orElseThrow(() -> new NoEmployeesInDatabaseException("There are no employees with these salaries in database"));
    }

    @Override
    public List<Employee> findEmployeesByProfession(EmployeeProfession employeeProfession) {
        return employeeRepository.findByProfession(employeeProfession)
                .orElseThrow(() -> new NoEmployeesInDatabaseException("There are no employees with this profession in database"));
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll().orElseThrow(NoEmployeesInDatabaseException::new);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = findEmployeeById(id);
        EmployeeAddress.deleteRelation(employee.getAddressId(), id);
        employeeRepository.delete(id);
    }

}
