package ru.latyshev.app.SpringAPI.dto;


import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ComicsDto {

    private Long id;

    @ApiModelProperty(value = "Title of the comic", required = true)
    @NotNull
    private String title;

    @ApiModelProperty(value = "Description of the comic", required = true)
    @NotNull
    private String description;

    @ApiModelProperty(value = "These are the characters that are present in this comic", required = false)
    private List<CharactersDto> marvelCharacters = new ArrayList<>();
}
