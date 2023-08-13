package org.Business.employee;

import java.util.Objects;

public class EmployeeLogin  implements Comparable<EmployeeLogin> {
    private String login;
    private String password;
    private Long employeeID;

    public EmployeeLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public EmployeeLogin(String login, String password, Long employeeID) {
        this.login = login;
        this.password = password;
        this.employeeID = employeeID;
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

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
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
