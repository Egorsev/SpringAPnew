package ru.latyshev.app.SpringAPI.convert;


import ru.latyshev.app.SpringAPI.dto.ComicsDateDto;
import ru.latyshev.app.SpringAPI.entity.ComicsDate;

import java.time.LocalDateTime;

public class ComicsDateConverter{

    public ComicsDate fromComicsDtoToComics(ComicsDateDto comicsDateDto){
        ComicsDate comicsDate=new ComicsDate();
        comicsDate.setId(comicsDateDto.getId());
        comicsDate.setType(comicsDateDto.getType());
        comicsDate.setDate(LocalDateTime.parse(comicsDateDto.getDate()));
        return comicsDate;
    }
    public ComicsDateDto fromComicsToComicsDto(ComicsDate comicsDate) {
        return ComicsDateDto.builder()
                .id(comicsDate.getId())
                .type(comicsDate.getType())
                .date(String.valueOf(comicsDate.getDate()))
                .build();
    }
}
