package ru.latyshev.app.SpringAPI.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.latyshev.app.SpringAPI.dto.CharactersDto;
import ru.latyshev.app.SpringAPI.entity.Character;

import java.awt.print.Pageable;
import java.util.Optional;


@Repository
public interface CharacterRepository extends PagingAndSortingRepository<Character, Long> {

    Page<CharactersDto> findAll(Pageable pageable);

    Optional<Character> findById(Long characterId, Sort sort);

}
