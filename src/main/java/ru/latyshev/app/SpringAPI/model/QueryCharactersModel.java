package ru.latyshev.app.SpringAPI.model;


import lombok.Data;

@Data
public class QueryCharactersModel {

    private Integer numberPage;
    private Integer pageSize;
    private Long comicId;
    private String orderBy;
}
