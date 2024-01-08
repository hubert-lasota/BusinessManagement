package org.hubert_lasota.BusinessManagement.repository;

import org.hubert_lasota.BusinessManagement.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, String> {

    Optional<User> findByEmployeeId(Long employeeId);

    Optional<List<User>> findByPassword(String password);

    Optional<List<User>> findByRole(String role);

}
