package ru.latyshev.app.SpringAPI.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="marvel_comics")
public class Comics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comicsId;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Long charactersId;

}
