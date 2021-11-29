package ru.latyshev.app.SpringAPI.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.latyshev.app.SpringAPI.entity.Character;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query("SELECT DISTINCT m FROM Character m " +
            "WHERE m.mod >= :modifiedFrom AND m.mod <= :modifiedTo ")
    Page<Character> findAllByModifiedDateAndOrdered(@Param("modifiedFrom") LocalDateTime modifiedFrom,
                                                          @Param("modifiedTo") LocalDateTime modifiedTo,
                                                          Pageable pageable);

    @Query("SELECT DISTINCT m FROM Character m LEFT JOIN m.comics c " +
            "WHERE c.id = :comicId AND m.mod >= :modifiedFrom AND m.mod <= :modifiedTo ")
    Page<Character> findAllByComicIdAndBetweenModifiedDateAndOrdered(@Param("comicId") Long id,
                                                                           @Param("modifiedFrom") LocalDateTime modifiedFrom,
                                                                           @Param("modifiedTo") LocalDateTime modifiedTo,
                                                                           Pageable pageable);

    @Query("SELECT DISTINCT m FROM Character m JOIN m.comics c JOIN c.dates d " +
            "WHERE d.date >= :dateFrom AND d.date <= :dateTo AND c.id = :comicId ")
    Page<Character> findAllByComicIdAndBetweenDatesAndOrdered(@Param("dateFrom") LocalDateTime dateFrom,
                                                                    @Param("dateTo") LocalDateTime dateTo,
                                                                    @Param("comicId") Long comicId,
                                                                    Pageable pageable);


    Optional<Character> findById(Long characterId);
}
