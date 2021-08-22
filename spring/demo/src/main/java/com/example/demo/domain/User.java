package com.example.demo.domain;

import com.example.demo.annotations.LatinAndSymbols;
import com.example.demo.annotations.UniqueEmail;
import com.example.demo.annotations.UniqueUsername;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name="id")
    private Long id;

    @Column
    @NotBlank(message = "Имя пользователя должно быть заполнено")
//    @UniqueUsername
    private String username;

    @Column
    @NotBlank(message = "Адрес электронной почты должен быть заполнен")
    @Email(message = "Неверный формат электронной почты")
//    @UniqueEmail(message = "Указанная электронная почта уже занята")
    private String email;

    @Column
    private String nickname;

    @Column
    @NotBlank(message = "Пароль должен быть заполнен")
    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Course> courses;

    @ManyToMany
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private AvatarImage avatarImage;

    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Authorisation authorisation;

    public User() {
    }

    public User(String username, Set<Course> courses) {
        this.username = username;
        this.courses = courses;
    }

    public User(Long id, String username, String email, String nickname, String password, Set<Course> courses, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.courses = courses;
        this.roles = roles;
    }

    public User(Long id, String username, String password, Set<Course> courses, Set<Role> roles, AvatarImage avatarImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.courses = courses;
        this.roles = roles;
        this.avatarImage = avatarImage;
    }

    public User(Long id, String username, String email, String nickname,
                String password, Set<Course> courses, Set<Role> roles,
                AvatarImage avatarImage) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.courses = courses;
        this.roles = roles;
        this.avatarImage = avatarImage;
    }

    public User(Long id, String username, String email, String nickname, String password, Set<Course> courses,
                Set<Role> roles, AvatarImage avatarImage, Authorisation authorisation) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.courses = courses;
        this.roles = roles;
        this.avatarImage = avatarImage;
        this.authorisation = authorisation;
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

    public AvatarImage getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(AvatarImage avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Authorisation getAuthorisation() {
        return authorisation;
    }

    public void setAuthorisation(Authorisation authorisation) {
        this.authorisation = authorisation;
    }
}
