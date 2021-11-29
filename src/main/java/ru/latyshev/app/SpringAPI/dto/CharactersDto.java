package ru.latyshev.app.SpringAPI.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;



@Data
@Builder
public class CharactersDto{

    @ApiModelProperty(value = " The unique ID of the character resource.")
    private Long id;

    @ApiModelProperty(value = "This is the character name")
    @NotBlank(message = "The name should not be empty")
    private String name;

    @ApiModelProperty(value = "This is the character discription")
    @NotBlank(message = "The description should not be empty")
    private String description;

    @ApiModelProperty(value = "This is the name of the full image on the server")
    private String imageName;

    @ApiModelProperty(value = "This is the date when the character was changed or added")
    private String mod;

}
