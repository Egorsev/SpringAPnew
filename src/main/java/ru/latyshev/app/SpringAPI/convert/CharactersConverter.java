package ru.latyshev.app.SpringAPI.convert;

import org.springframework.stereotype.Component;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.entity.Character;
import ru.latyshev.app.SpringAPI.service.DateHelperService;


@Component
public class CharactersConverter implements DateHelperService {
    public Character fromCharactersDtoToCharacter(CharactersDto charactersDto){
        Character character=new Character();
        character.setId(charactersDto.getId());
        character.setName(charactersDto.getName());
        character.setDescription(charactersDto.getDescription());
        character.setImageName(charactersDto.getImageName());
        character.setMod(parseStringDateFormatToLocalDateTime(charactersDto.getMod()));
        return character;
    }
    public CharactersDto fromCharactersToCharactersDto(Character character){
        return CharactersDto.builder()
                .id(character.getId())
                .name(character.getName())
                .description(character.getDescription())
                .imageName(character.getImageName())
                .build();
    }

}
