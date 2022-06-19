package com.example.doannmcnpm.service;


import com.example.doannmcnpm.model.Role;
import com.example.doannmcnpm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    public List<Role> listRole() {
        return roleRepository.findAll();
    }
}
