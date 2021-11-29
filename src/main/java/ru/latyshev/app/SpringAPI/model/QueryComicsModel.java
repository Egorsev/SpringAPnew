package ru.latyshev.app.SpringAPI.model;


import lombok.Data;

@Data
public class QueryComicsModel {

    private Long comicId;
    private Integer numberPage;
    private Integer pageSize;
    private String title;
    private String dateStart;
    private String dateEnd;
    private String orderBy;
}
