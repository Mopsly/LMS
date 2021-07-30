package com.example.demo.domain;

import com.example.demo.annotations.Lang;
import com.example.demo.annotations.TitleCase;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(
        name = "courses"
)
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
    @OneToMany(
            mappedBy = "course",
            cascade = {CascadeType.ALL}
    )
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
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Lesson> getLessons() {
        return this.lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
