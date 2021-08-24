package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {

    private Long id;
    @NotBlank(message = "необходимо указать имя модуля")
    private String title;
    private String description;
    @NotNull(message = "Необходимо выбрать курс")
    private Long courseId;
}
