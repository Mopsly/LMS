package com.example.demo.domain;

import com.example.demo.annotations.Lang;
import com.example.demo.annotations.TitleCase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Автор курса должен быть заполнен")
    private String author;

<<<<<<< spring/demo/src/main/java/com/example/demo/domain/Course.java
    @Column
    @NotBlank(message = "Название курса должен быть заполнен")
    @TitleCase(lang = Lang.ANY)
=======
    @NotBlank(message = "Название курса должен быть заполнен")
    @TitleCase(lang = Lang.EN)
>>>>>>> spring/demo/src/main/java/com/example/demo/domain/Course.java
    private String title;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    @ManyToMany
    private Set<User> users;

    public Course() {
    }

    public Course(Long id, String author, String title) {
        this.id = id;
        this.author = author;
        this.title = title;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
