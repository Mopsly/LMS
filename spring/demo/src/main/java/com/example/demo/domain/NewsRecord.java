package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.domain.User;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class NewsRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User author;

    @Column
    @NotNull
    @NotBlank
    private String title;

    @Column
    private Date publicationDate;

    @Column
    @NotNull
    @NotBlank
    private String tag;

    //private Content content;
}
