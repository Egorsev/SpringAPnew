package ru.latyshev.app.SpringAPI.entity;



import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime mod;

    public void setComics(Comics com) {
        if (com!=null){
            this.comics.add(com);
        }
    }

    public Character(){

    }


}
