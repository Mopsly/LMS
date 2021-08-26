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

    public void init(){
        if(category == null){
            category = "any";
        }
        if(author == null){
            author = "any";
        }
        if(sortOrder == null){
            sortOrder = "title";
        }
    }
}
