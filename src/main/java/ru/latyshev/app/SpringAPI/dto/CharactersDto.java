package ru.latyshev.app.SpringAPI.dto;


import lombok.Builder;
import lombok.Data;
import ru.latyshev.app.SpringAPI.entity.Character;

@Data
@Builder
public class CharactersDto{
    private Long id;
    private String name;
    private String description;
    private String imageName;
}
