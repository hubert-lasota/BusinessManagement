package org.hubert_lasota.BusinessManagement.entity.address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeAddress {
    private static final Map<Long, List<Long>> addressRelationToEmployees = new HashMap<>();

    private EmployeeAddress() { }

    public static void addRelation(Long addressId, Long employeeId) {
        if(addressRelationToEmployees.containsKey(addressId)) {
            addressRelationToEmployees.get(addressId).add(employeeId);
        } else {
            List<Long> employees = new ArrayList<>();
            employees.add(employeeId);
            addressRelationToEmployees.put(addressId, employees);
        }

    }

    public static boolean isAddressAssociatedToAnyEmployee(Long addressId) {
        return addressRelationToEmployees.containsKey(addressId);
    }

    public static boolean isAddressAssociatedToEmployee(Long addressId, Long employeeId) {
        if(!isAddressAssociatedToAnyEmployee(addressId)) {
            return false;
        }

        return addressRelationToEmployees.get(addressId).contains(employeeId);
    }

    public static void deleteRelation(Long addressId, Long employeeId) {
        if(!isAddressAssociatedToAnyEmployee(addressId)) {
            return;
        }
        List<Long> employees = addressRelationToEmployees.get(addressId);
        if(employees.size() > 1) {
            employees.remove(employeeId);
        } else {
            addressRelationToEmployees.remove(addressId, employees);
        }
    }

}
