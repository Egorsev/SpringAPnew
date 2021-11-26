package ru.latyshev.app.SpringAPI.service;

import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;

import java.util.List;

public interface ComicsService {
    ComicsDto createNemComics(ComicsDto comicsDto);
    List<ComicsDto> getAllComics();
    ComicsDto getComicsById(Long id);
    ComicsDto updateComicsById(Long id, ComicsDto comicsDto);
    void deleteComics(Long id);

}
