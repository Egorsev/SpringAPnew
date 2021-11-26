package ru.latyshev.app.SpringAPI.model;

import lombok.Data;


@Data
public class QueryComicsModel {

        private Integer numberPage;
        private Integer pageSize;
        private Long comicsId;
        private String orderBy;

}
