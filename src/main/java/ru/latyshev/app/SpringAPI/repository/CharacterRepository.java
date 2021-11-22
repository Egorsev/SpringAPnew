package ru.latyshev.app.SpringAPI.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.latyshev.app.SpringAPI.entity.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
        Character findByName(String name);

}
