package com.example.demo.dto;

public class LessonDto {
    private Long id;
    private String title;
    private String text;
    private Long courseId;

    public LessonDto() {
    }

    public LessonDto(Long courseId) {
        this.courseId = courseId;
    }

    public LessonDto(Long id, Long courseId, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.courseId = courseId;
    }

    public LessonDto(Long id, String title, Long courseId) {
        this.id = id;
        this.title = title;
        this.courseId = courseId;
    }

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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCourseId() {
        return this.courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
