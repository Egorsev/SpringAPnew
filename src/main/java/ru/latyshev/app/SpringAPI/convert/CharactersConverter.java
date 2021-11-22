package ru.latyshev.app.SpringAPI.convert;

import org.springframework.stereotype.Component;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.entity.Character;


@Component
public class CharactersConverter {
    public Character fromCharactersDtoToCharacter(CharactersDto charactersDto){
        Character character=new Character();
        character.setId(charactersDto.getId());
        character.setName(charactersDto.getName());
        character.setDescription(charactersDto.getDescription());
        character.setImageName(charactersDto.getImageName());
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
