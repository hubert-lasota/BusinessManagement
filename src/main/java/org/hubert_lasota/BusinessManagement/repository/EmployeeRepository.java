package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.employee.Employee;
import org.hubert_lasota.BusinessManagement.entity.employee.EmployeeProfession;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends Repository<Employee, Long> {

    Optional<List<Employee>> findByFirstName(String firstName);

    Optional<List<Employee>> findByLastName(String lastName);

    Optional<List<Employee>> findByYearOfBirth(Year dateOfBirth);

    Optional<List<Employee>> findBetweenSalaries(BigDecimal lowerSalary, BigDecimal upperSalary);

    Optional<List<Employee>> findByProfession(EmployeeProfession employeeProfession);

}
