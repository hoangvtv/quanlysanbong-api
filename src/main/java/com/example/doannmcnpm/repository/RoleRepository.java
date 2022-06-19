package com.example.doannmcnpm.repository;

import com.example.doannmcnpm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findById(int roleid);

}
