package com.example.demo.dto;

import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsRecordDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Автор должен быть указан")
    private Long authorId;

    @NotNull
    @NotBlank(message = "Название должно быть указано")
    private String title;


    private Date publicationDate;

    @NotNull
    @NotBlank(message = "Тэг должен быть указан")
    private String tag;
}
