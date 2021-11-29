package ru.latyshev.app.SpringAPI.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ComicsDateDto {
    @ApiModelProperty(value = " The unique ID of the comic date resource")
    private Long id;

    @ApiModelProperty(value = "A description of the date (e.g. on sale date, FOC date)")
    @NotBlank
    private String type;

    @ApiModelProperty(value = "Format date is 'yyyy-MM-dd hh:mm:ss'")
    @NotBlank
    private String date;
}
