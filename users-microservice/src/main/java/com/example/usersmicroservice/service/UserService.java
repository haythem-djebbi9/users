package com.example.usersmicroservice.service;

import java.util.List;

import com.example.usersmicroservice.entities.Role;
import com.example.usersmicroservice.entities.User;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername (String username);
    Role addRole(Role role);
    User addRoleToUser(long id, Role r);
    List<User> findAllUsers();

    void deleteUser(long id);
    User removeRoleFromUser(long id, Role r);
    List<Role> findAllRoles();

    Role findRoleById(Long id);

    User findUserById(Long id);

    User activateUser(String username, String code);

}