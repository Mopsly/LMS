package com.example.demo.service;

import com.example.demo.dao.LessonRepository;
import com.example.demo.dao.RoleRepository;
import com.example.demo.domain.Role;
import com.example.demo.dto.UserDto;
import com.example.demo.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RoleServiceTest {

    RoleService roleService;

    @BeforeEach
    public void setUp() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L,"ROLE_USER",new HashSet<>()));
        roles.add(new Role(2L,"ROLE_ADMIN", new HashSet<>()));

        RoleRepository roleRepositoryMock = Mockito.mock(RoleRepository.class);
        Mockito.when(roleRepositoryMock.getById(1L)).thenReturn(roles.get(0));
        Mockito.when(roleRepositoryMock.getById(2L)).thenReturn(roles.get(1));

        roleService = new RoleService(roleRepositoryMock);
    }

    @Test
    public void setDefaultRoleTest(){
        UserDto user = new UserDto(1L,"user","email","nickname","password","password",new HashSet<>(),new HashSet<>());
        assertThat(roleService.setDefaultRole(user).getRoles(),
                hasItem(hasProperty("name",is("ROLE_USER"))));
    }
}
