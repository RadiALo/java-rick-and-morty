package com.radialo.rickandmorty.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.radialo.rickandmorty.dto.ApiExternalDto;
import com.radialo.rickandmorty.dto.MovieCharacterExternalDto;
import com.radialo.rickandmorty.model.MovieCharacter;
import com.radialo.rickandmorty.repository.MovieCharacterRepository;
import com.radialo.rickandmorty.service.MovieCharacterService;
import com.radialo.rickandmorty.service.mapper.MovieCharacterMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private static final String URL = "https://rickandmortyapi.com/api/character";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Scheduled(cron = "0 8 * * * ?")
    @PostConstruct
    @Override
    public void sync() {
        List<MovieCharacterExternalDto> characterExternalDtos = new ArrayList<>();
        ApiExternalDto apiExternalDto;
        String url = URL;
        do {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                apiExternalDto = objectMapper.readValue(response.getEntity().getContent(),
                        ApiExternalDto.class);
                characterExternalDtos.addAll(List.of(apiExternalDto.getResults()));
                url = apiExternalDto.getInfo().getNext();
            } catch (IOException e) {
                throw new RuntimeException("Can't fetch data from " + URL, e);
            }
        } while (url != null);
        saveDtos(characterExternalDtos);
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = random.nextLong(count);
        return movieCharacterRepository.getReferenceById(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }

    private void saveDtos(List<MovieCharacterExternalDto> characterExternalDtos) {
        Set<Long> existedIds = movieCharacterRepository.findAllByIdIn(characterExternalDtos.stream()
                        .map(MovieCharacterExternalDto::getId).collect(Collectors.toSet()))
                .stream().map(MovieCharacter::getExternalId)
                .collect(Collectors.toSet());
        movieCharacterRepository.saveAll(characterExternalDtos.stream()
                .filter(c -> !existedIds.contains(c.getId()))
                .map(movieCharacterMapper::fromExternalDto)
                .collect(Collectors.toList()));
    }
}
