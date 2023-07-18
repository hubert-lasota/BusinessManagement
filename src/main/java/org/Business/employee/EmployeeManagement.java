package org.Business.employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagement {
    private static final List<EmployeeLogin> employeesLogin = new ArrayList<>();

    public static void addEmployeeLogin(EmployeeLogin employeeLogin) {
        if(employeesLogin.contains(employeeLogin)) {
            System.out.println("This Login actually exists in List.\nYou need to change login.");
            return;
        }
        employeesLogin.add(employeeLogin);
    }

    public static void removeEmployeeLogin(EmployeeLogin employeeLogin) {
        employeesLogin.remove(employeeLogin);
    }

    public static void removeEmployeeLogin(int ID) {
        employeesLogin.removeIf(employeeLogin -> employeeLogin.getEmployeeData().getID() == ID);
    }


}
