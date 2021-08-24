package com.example.demo.utils.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsFilter {

    private String hashTag;

    private Long authorId;

    private String sortOrder;
}
