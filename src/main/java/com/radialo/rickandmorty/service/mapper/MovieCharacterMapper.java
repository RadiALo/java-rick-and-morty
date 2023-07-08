package com.radialo.rickandmorty.service.mapper;

import com.radialo.rickandmorty.dto.MovieCharacterExternalDto;
import com.radialo.rickandmorty.dto.MovieCharacterResponseDto;
import com.radialo.rickandmorty.model.Gender;
import com.radialo.rickandmorty.model.MovieCharacter;
import com.radialo.rickandmorty.model.Status;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {
    public MovieCharacterResponseDto toDto(MovieCharacter movieCharacter) {
        MovieCharacterResponseDto movieCharacterDto = new MovieCharacterResponseDto();
        movieCharacterDto.setId(movieCharacter.getId());
        movieCharacterDto.setExternalId(movieCharacter.getExternalId());
        movieCharacterDto.setName(movieCharacter.getName());
        movieCharacterDto.setStatus(movieCharacter.getStatus().name());
        movieCharacterDto.setGender(movieCharacter.getGender().name());
        return movieCharacterDto;
    }

    public MovieCharacter fromExternalDto(MovieCharacterExternalDto externalDto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(externalDto.getName());
        movieCharacter.setGender(Gender.valueOf(externalDto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(externalDto.getStatus().toUpperCase()));
        movieCharacter.setExternalId(externalDto.getId());
        return movieCharacter;
    }
}
