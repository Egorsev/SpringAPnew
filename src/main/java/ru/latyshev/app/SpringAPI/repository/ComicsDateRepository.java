package ru.latyshev.app.SpringAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.latyshev.app.SpringAPI.entity.ComicsDate;


@Repository
public interface ComicsDateRepository extends JpaRepository<ComicsDate,Long> {
}
