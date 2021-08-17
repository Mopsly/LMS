package com.example.demo.domain;

import com.example.demo.annotations.Lang;
import com.example.demo.annotations.TitleCase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Автор курса должен быть заполнен")
    private String author;

    @Column
    @NotBlank(message = "Название курса должен быть заполнен")
    @TitleCase(lang = Lang.ANY)
    private String title;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    @ManyToMany
    private Set<User> users;

    @OneToOne(mappedBy = "course", cascade = CascadeType.REMOVE)
    private CourseImage courseImage;

    public Course() {
    }

    public Course(Long id, String author, String title) {
        this.id = id;
        this.author = author;
        this.title = title;
    }

    public Course(Long id, String author, String title, List<Lesson> lessons, Set<User> users, CourseImage courseImage) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.lessons = lessons;
        this.users = users;
        this.courseImage = courseImage;
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

    public CourseImage getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(CourseImage courseImage) {
        this.courseImage = courseImage;
    }
}
