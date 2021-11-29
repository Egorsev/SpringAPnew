package ru.latyshev.app.SpringAPI.service;

import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.exception.CharacterNotFoundException;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.exception.NotValidParametersException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.model.QueryCharactersModel;
import ru.latyshev.app.SpringAPI.model.QueryComicsModel;

import java.util.List;

public interface ComicsService {
    ModelDataContainer<ComicsDto> createNemComics(ComicsDto model) throws NotValidParametersException;
    ModelDataContainer<ComicsDto> getAllComics(QueryComicsModel model) throws ComicsNotFoundException;
    ModelDataContainer<ComicsDto> getComicsById(Long id) throws ComicsNotFoundException;
    ModelDataContainer<CharactersDto> getCharactersByModel(QueryCharactersModel model) throws CharacterNotFoundException;
    ModelDataContainer<ComicsDto> updateComicsById(Long id, ComicsDto model) throws NotValidParametersException;
    void deleteComics(Long id);

}
