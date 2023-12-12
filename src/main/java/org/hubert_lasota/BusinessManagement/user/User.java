package org.hubert_lasota.BusinessManagement.user;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User {
    private Long employeeId;
    private String username;
    private String password;
    private List<String> roles;

    public User(String username, String password, String... roles) {
        this.username = username;
        this.password = password;
        this.roles = Arrays.asList(roles);
    }

    public User(Long employeeId, String username, String password, String... roles) {
        this(username, password, roles);
        this.employeeId = employeeId;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
