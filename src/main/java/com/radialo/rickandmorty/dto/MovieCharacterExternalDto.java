package com.radialo.rickandmorty.dto;

import lombok.Data;

@Data
public class MovieCharacterExternalDto {
    private Long id;
    private String name;
    private String status;
    private String gender;
}
