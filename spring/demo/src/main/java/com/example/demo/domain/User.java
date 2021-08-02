package com.example.demo.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Имя пользователя должно быть заполнено")
    private String username;

    @Column
    @NotBlank(message = "Пароль должен быть заполнен")
    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Course> courses;

    @ManyToMany
    private Set<Role> roles;

    public User() {
    }

    public User(String username, Set<Course> courses) {
        this.username = username;
        this.courses = courses;
    }

    public User(Long id, String username, String password, Set<Course> courses, Set<Role> roles) {
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

    public Set<Course> getCourses() {
        return this.courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            User user = (User) o;
            if (this.id.equals(user.id)
                    && (this.username.equals(user.username))
                    && (this.password.equals(user.password))) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.id,this.username,this.password);
    }
}
