

package com.example.demo.dto;

import com.example.demo.annotations.FieldMatch;
import com.example.demo.annotations.LatinAndSymbols;
import com.example.demo.annotations.UniqueEmail;
import com.example.demo.annotations.UniqueUsername;
import com.example.demo.domain.Course;
import com.example.demo.domain.Role;
import com.sun.istack.NotNull;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.*;
import java.util.Objects;
import java.util.Set;

@FieldMatch(first = "password", second = "passwordConfirm", message = "Пароли должны совпадать")
@UniqueEmail(email = "email", id = "id",
        message = "Данный email уже зарегистрирован. Перейдите в форму авторизации и нажмите на кнопку восстановления пароля")
@UniqueUsername(username = "username", id = "id",
        message = "Данное имя пользователя уже занято")
public class UserDto {
    private Long id;

    @NotNull
    @NotBlank(message = "Имя пользователя должно быть заполнено")
    @LatinAndSymbols(message = "Логин должен включать в себя латиницу и/или спецсимволы")
    private String username;

    @NotNull
    @NotBlank(message = "Email должен быть указан")
    @Email(message = "Неверный формат  почты")
    private String email;

    @Pattern(regexp = "^[A-Za-zА-Яа-я]*$",
            message = "Никнейм может содержать латиницу и/или кириллицу")
    private String nickname;

    @NotNull
    @NotBlank(message = "Пароль должен быть заполнен")
    @Size(min = 8, message = "Пароль не должен состоять менее чем из 8 символов")
    @LatinAndSymbols(message = "Пароль должен включать в себя латиницу и/или спецсимволы")
    private String password;

    @NotBlank(message = "Номер телефона должен быть указан")
    @Pattern(regexp = "^$|^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$",
            message = "Введен некорректный номер телефона")
    private String phone;

    @Transient
    private String passwordConfirm;

    private Set<Course> courses;

    private Set<Role> roles;

    public UserDto() {
    }


    public UserDto(Long id, String username, String email, String nickname, String password, String passwordConfirm, Set<Course> courses, Set<Role> roles, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.courses = courses;
        this.roles = roles;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return id.equals(userDto.id)
                && username.equals(userDto.username)
                && email.equals(userDto.email)
                && Objects.equals(nickname, userDto.nickname)
                && password.equals(userDto.password)
                && passwordConfirm.equals(userDto.passwordConfirm)
                && Objects.equals(courses, userDto.courses)
                && Objects.equals(roles, userDto.roles)
                && phone.equals(userDto.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, nickname, password, passwordConfirm, courses, roles, phone);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", courses=" + courses +
                ", roles=" + roles +
                ", phone=" + phone +
                '}';
    }
}


