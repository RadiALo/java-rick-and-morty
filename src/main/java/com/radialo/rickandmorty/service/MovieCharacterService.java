package com.radialo.rickandmorty.service;

import com.radialo.rickandmorty.model.MovieCharacter;
import java.util.List;

public interface MovieCharacterService {
    void sync();

    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
