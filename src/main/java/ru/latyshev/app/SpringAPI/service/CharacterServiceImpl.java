package ru.latyshev.app.SpringAPI.service;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.latyshev.app.SpringAPI.convert.CharactersConverter;
import ru.latyshev.app.SpringAPI.convert.ComicsConverter;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.entity.Character;
import ru.latyshev.app.SpringAPI.exception.ComicsNotFoundException;
import ru.latyshev.app.SpringAPI.model.ModelDataContainer;
import ru.latyshev.app.SpringAPI.repository.CharacterRepository;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService  {

    private final CharacterRepository characterRepository;
    private final CharactersConverter charactersConverter;
    private final ComicsConverter comicsConverter;


    @Override
    @Transactional
    public CharactersDto createNewCharacter(CharactersDto charactersDto) {
        Character saveCharacter=characterRepository.save(
                charactersConverter.fromCharactersDtoToCharacter(charactersDto));
        return charactersConverter.fromCharactersToCharactersDto(saveCharacter);
    }


    @Override
    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }



    @Override
    @Transactional
    public ModelDataContainer<CharactersDto> findById(Long id) {
        Optional<Character> character=characterRepository.findById(id);
        if (character.isPresent()){
            List<CharactersDto> list=new ArrayList<>();
            list.add(charactersConverter.fromCharactersToCharactersDto(character.get()));
            ModelDataContainer<CharactersDto>model=new ModelDataContainer<>();
            model.setResults(list);
            return model;
        } else{
            return null;
        }
    }
/*
    @Override
    public CharactersDto findByName(String name) {
        Character character=characterRepository.findByName(name);
        if (character!=null){
            return charactersConverter.fromCharactersToCharactersDto(character);
        } else
            return null;
    }


 */
    @Override
    public List<CharactersDto> findAllCharacters(int pageNumber, int pageSize) {
        Pageable pageable= (Pageable) PageRequest.of(pageNumber,pageSize);
        return characterRepository.findAll(pageable).getContent();

    }

    @Override
    public ModelDataContainer<ComicsDto> findComicsByCharacterId(Long characterId) throws ComicsNotFoundException {
        Sort sort= Sort.by(Sort.Direction.DESC,"characterId");
        Optional<Character> characterOptional =characterRepository.findById(characterId, sort);
        if (characterOptional.isPresent()){
            ModelDataContainer<ComicsDto> model=new ModelDataContainer<>();
            model.setResults(
            characterOptional.get().getComics()
                    .stream()
                    .map(comicsConverter::fromComicsToComicsDto)
                    .collect(Collectors.toList()));
            return model;
        } else {
           throw new ComicsNotFoundException("Character with id"+characterId+" not found");
        }
    }

        @Override
        public CharactersDto updateMarvelCharacter(Long id, CharactersDto charactersDto) {
            charactersDto.setId(id);
            return this.createNewCharacter(charactersDto);
        }



}
