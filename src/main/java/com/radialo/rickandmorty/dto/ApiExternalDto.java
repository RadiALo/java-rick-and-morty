package com.radialo.rickandmorty.dto;

import lombok.Data;

@Data
public class ApiExternalDto {
    private InfoExternalDto info;
    private MovieCharacterExternalDto[] results;
}
