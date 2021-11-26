package ru.latyshev.app.SpringAPI.service;


import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;

import java.util.List;

public interface CharacterService {
    CharactersDto createNewCharacter(CharactersDto charactersDto);
    void deleteCharacter(Long id);
    ModelDataContainer<CharactersDto> findById(Long id);
 //   CharactersDto findByName(String name);
    List<CharactersDto> findAllCharacters(int pageNumber, int pageSize);
    ModelDataContainer<ComicsDto> findComicsByCharacterId(Long characterId) throws ComicsNotFoundException;
    CharactersDto updateMarvelCharacter(Long id, CharactersDto charactersDto );
}
