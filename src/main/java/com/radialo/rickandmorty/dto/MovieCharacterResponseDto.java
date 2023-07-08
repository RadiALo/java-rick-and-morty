package com.radialo.rickandmorty.dto;

import lombok.Data;

@Data
public class MovieCharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private String status;
    private String gender;
}
