package com.example.doannmcnpm.controller;


import com.example.doannmcnpm.model.Role;
import com.example.doannmcnpm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/list")
    public List<Role> listRole()    {
        return roleService.listRole();
    }
}
