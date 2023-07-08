package com.radialo.rickandmorty.controller;

import com.radialo.rickandmorty.dto.MovieCharacterResponseDto;
import com.radialo.rickandmorty.model.MovieCharacter;
import com.radialo.rickandmorty.service.MovieCharacterService;
import com.radialo.rickandmorty.service.mapper.MovieCharacterMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper mapper;

    @GetMapping("/random")
    @ApiOperation(value = "Get a random character")
    public MovieCharacterResponseDto getRandom() {
        MovieCharacter movieCharacter = movieCharacterService.getRandomCharacter();
        return mapper.toDto(movieCharacter);
    }

    @GetMapping("/by-name")
    @ApiOperation(value = "Get a list of characters containing a string in the name")
    public List<MovieCharacterResponseDto> findAllByName(
            @ApiParam(value = "The String that should be contained in the character's name")
            @RequestParam String namePart) {
        return movieCharacterService.findAllByNameContains(namePart).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
