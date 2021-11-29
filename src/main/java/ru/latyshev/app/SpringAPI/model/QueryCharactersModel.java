package ru.latyshev.app.SpringAPI.model;


import lombok.Data;

@Data
public class QueryCharactersModel {

    private Integer numberPage;
    private Integer pageSize;
    private String modStart;
    private String modEnd;
    private Long comicsId;
    private String orderBy;
}
