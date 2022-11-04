package com.example.demo.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(Role.Roles role);


}
