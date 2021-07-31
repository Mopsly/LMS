package com.example.demo.service;

import com.example.demo.dao.RoleRepository;
import com.example.demo.domain.Role;
import com.example.demo.dto.UserDto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto setDefaultRole(UserDto user) {
        Set<Role> roles = user.getRoles();
        if (roles == null) {
            roles = new HashSet();
        }

        Role role = roleRepository.getById(1L);
        roles.add(role);
        user.setRoles(roles);
        return user;
    }

    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }
}
