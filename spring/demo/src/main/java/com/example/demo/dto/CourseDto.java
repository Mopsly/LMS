package com.example.demo.dto;

public class CourseDto {
    private Long id;
    private String title;
    private String author;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public CourseDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
