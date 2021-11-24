package ru.latyshev.app.SpringAPI.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="marvel_comics")
public class Comics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Long characterId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "marvel_comics_characters", joinColumns = @JoinColumn(name="comics_id"),
    inverseJoinColumns = @JoinColumn(name = "marvel_characters_id"))
    private Set<Character> characters=new HashSet<>();

    public Comics() {
    }
}
