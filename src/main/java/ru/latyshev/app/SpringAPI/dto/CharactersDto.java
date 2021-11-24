package ru.latyshev.app.SpringAPI.dto;


import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import ru.latyshev.app.SpringAPI.entity.Character;

@Data
@Builder
public class CharactersDto{

    @ApiModelProperty(value = " The unique ID of the character resource.", required = true)
    private Long id;

    @ApiModelProperty(value = "This is the character's name", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(value = "This is the character's discription", required = true)
    @NotNull
    private String description;

    @ApiModelProperty(value = "This is the name of the full image on the server", required = false)
    private String imageName;
}
