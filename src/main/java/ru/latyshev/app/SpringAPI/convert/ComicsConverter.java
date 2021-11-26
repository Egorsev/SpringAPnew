package ru.latyshev.app.SpringAPI.convert;


import org.springframework.stereotype.Component;
import ru.latyshev.app.SpringAPI.dto.ComicsDto;
import ru.latyshev.app.SpringAPI.entity.Comics;

@Component
public class ComicsConverter {

    public Comics fromComicsDtoToComics(ComicsDto comicsDto){
        Comics comics=new Comics();
        comics.setId(comics.getId());
        comics.setTitle(comicsDto.getTitle());
        comics.setDescription(comics.getDescription());
        return comics;
    }
    public ComicsDto fromComicsToComicsDto(Comics comics) {
        return ComicsDto.builder()
                .id(comics.getId())
                .title(comics.getTitle())
                .description(comics.getDescription())
                .build();
    }
}
