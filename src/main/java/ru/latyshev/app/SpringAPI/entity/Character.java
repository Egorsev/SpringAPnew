package ru.latyshev.app.SpringAPI.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="marvel_characters")
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

    @ManyToMany(mappedBy = "characters",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Comics> comics=new HashSet<>();

    public Character(){

    }


}
