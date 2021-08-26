package com.example.demo.utils.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseFilter {

    private String titlePrefix;

    private String category;

    private String author;

    private String sortOrder;

public boolean hasEmptyFields(){
    return (category == null || author == null || sortOrder ==null);
}
}
