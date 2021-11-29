package ru.latyshev.app.SpringAPI.service;


import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.entity.Character;
import ru.latyshev.app.SpringAPI.exception.CharacterNotFoundException;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.exception.NotValidParametersException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.model.QueryCharactersModel;

import java.util.List;

public interface CharacterService {
    ModelDataContainer<CharactersDto> createNewCharacter(CharactersDto model) throws NotValidParametersException;
    void deleteCharacter(Long id);
    ModelDataContainer<CharactersDto> findById(Long id);
    List<Character> findAllCharacters();
    ModelDataContainer<ComicsDto> findComicsByCharacterId(Long characterId) throws ComicsNotFoundException;
    ModelDataContainer<CharactersDto> updateMarvelCharacter(Long id, CharactersDto model ) throws NotValidParametersException;
    ModelDataContainer<CharactersDto> getCharactersByModel(QueryCharactersModel model) throws CharacterNotFoundException;
}
