

package com.example.demo.dto;

import com.example.demo.domain.Course;
import com.example.demo.domain.Role;
import java.util.Set;

public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Set<Course> courses;
    private Set<Role> roles;

    public UserDto() {
    }

    public UserDto(Long id, String username, String password, Set<Course> courses, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.courses = courses;
        this.roles = roles;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
