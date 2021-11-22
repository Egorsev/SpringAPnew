package ru.latyshev.app.SpringAPI.entity;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="marvel_characters")
@Builder
public class Character{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String imageName;

    public Character(){

    }


}
