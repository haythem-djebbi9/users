package com.example.usersmicroservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.usersmicroservice.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
Role findByRole(String role);

@Query("select r from Role r where r.role_id = ?1")
Role findRoleById(Long id);

}