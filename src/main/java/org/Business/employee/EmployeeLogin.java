package org.Business.employee;

import java.util.Objects;

public class EmployeeLogin  implements Comparable<EmployeeLogin> {
    private String login;
    private String password;
    private EmployeeData employeeData;

    public EmployeeLogin(String login, String password, EmployeeData employeeData) {
        this.login = login;
        this.password = password;
        this.employeeData = employeeData;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeData getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(EmployeeData employeeData) {
        this.employeeData = employeeData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeLogin that = (EmployeeLogin) o;
        return login.equals(that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }


    @Override
    public int compareTo(EmployeeLogin o) {
        return login.compareTo(o.login);
    }
}
