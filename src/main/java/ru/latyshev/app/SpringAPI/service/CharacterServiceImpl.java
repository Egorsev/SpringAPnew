package ru.latyshev.app.SpringAPI.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.latyshev.app.SpringAPI.convert.CharactersConverter;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.entity.Character;
import ru.latyshev.app.SpringAPI.repository.CharacterRepository;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService  {

    private final CharacterRepository characterRepository;
    private final CharactersConverter charactersConverter;


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
    public CharactersDto findById(Long id) {
        return null;
    }

    @Override
    public CharactersDto findByName(String name) {
        Character character=characterRepository.findByName(name);
        if (character!=null){
            return charactersConverter.fromCharactersToCharactersDto(character);
        } else
            return null;
    }

    @Override
    public List<CharactersDto> findAllCharacters() {
        return characterRepository.findAll()
                .stream()
                .map(charactersConverter::fromCharactersToCharactersDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComicsDto findComicsByCharacterId(Long characterId) {

    }

    @Override
    public CharactersDto updateMarvelCharacter(Long id, CharactersDto charactersDto) {
        charactersDto.setId(id);
        return this.createNewCharacter(charactersDto);
    }

}
