package ru.latyshev.app.SpringAPI.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ComicsDto {

    private Long id;

    @ApiModelProperty(value = "Title of the comics")
    @NotBlank(message = "The title should not be empty")
    private String title;

    @ApiModelProperty(value = "Description of the comics")
    @NotBlank(message = "The description should not be empty")
    private String description;

    @ApiModelProperty(value = "The characters that are present in this comic")
    private List<CharactersDto> marvelCharacters = new ArrayList<>();
}
