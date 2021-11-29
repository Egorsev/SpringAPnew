package ru.latyshev.app.SpringAPI.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.latyshev.app.SpringAPI.entity.Comics;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ComicsRepository extends JpaRepository<Comics,Long> {

    Optional<Comics> findById(Long id);

    @Query("SELECT DISTINCT c FROM Comics c JOIN c.dates d " +
            "WHERE d.date >= :dateFrom AND d.date <= :dateTo AND c.title = :title ")
    Page<Comics> findAllByTitleAndBetweenDatesAndOrdered(@Param("dateFrom") LocalDateTime dateFrom,
                                                        @Param("dateTo") LocalDateTime dateTo,
                                                        @Param("title") String title,
                                                        Pageable pageable);

    @Query("SELECT DISTINCT c FROM Comics c JOIN c.dates d " +
            "WHERE d.date >= :dateFrom AND d.date <= :dateTo ")
    Page<Comics> findAllBetweenDatesAndOrdered(@Param("dateFrom") LocalDateTime dateFrom,
                                              @Param("dateTo") LocalDateTime dateTo,
                                              Pageable pageable);
}
