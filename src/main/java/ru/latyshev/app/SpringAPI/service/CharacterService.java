package ru.latyshev.app.SpringAPI.service;


import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;

import java.util.List;

public interface CharacterService {
    CharactersDto createNewCharacter(CharactersDto charactersDto);
    void deleteCharacter(Long id);
    CharactersDto findById(Long id);
    CharactersDto findByName(String name);
    List<CharactersDto> findAllCharacters();
    ComicsDto findComicsByCharacterId(Long characterId);
    CharactersDto updateMarvelCharacter(Long id, CharactersDto charactersDto );
}
