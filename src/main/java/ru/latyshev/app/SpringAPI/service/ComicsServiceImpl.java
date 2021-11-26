package ru.latyshev.app.SpringAPI.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.latyshev.app.SpringAPI.convert.ComicsConverter;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.entity.Character;
import ru.latyshev.app.SpringAPI.entity.Comics;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.exception.NotValidParametersException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.model.QueryCharactersModel;
import ru.latyshev.app.SpringAPI.repository.CharacterRepository;
import ru.latyshev.app.SpringAPI.repository.ComicsRepository;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ComicsServiceImpl implements ComicsService{

    private final ComicsDto comicsDto;
    private final ComicsRepository comicsRepository;
    private final CharacterRepository characterRepository;
    private final ComicsConverter comicsConverter;


    @Override
    public ComicsDto createNemComics(ComicsDto comicsDto) {
        Comics saveComics=comicsRepository.save(comicsConverter.fromComicsDtoToComics(comicsDto));
        return comicsConverter.fromComicsToComicsDto(saveComics);
    }

    @Override
    public List<ComicsDto> getAllComics() {
        return comicsRepository.findAll()
                .stream()
                .map(comicsConverter::fromComicsToComicsDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComicsDto getComicsById(Long id) {
        Optional<Comics> comics=comicsRepository.findById(id);
        if (comics.isPresent()){
            return comicsConverter.fromComicsToComicsDto(comics.get());
        } else{
            return null;
        }
    }

    @Override
    public ComicsDto updateComicsById(Long id, ComicsDto comicsDto) {
       comicsDto.setId(id);
        return this.createNemComics(comicsDto);
    }

    @Override
    public void deleteComics(Long id) {
        comicsRepository.deleteById(id);

    }

}
