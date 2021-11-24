package ru.latyshev.app.SpringAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.latyshev.app.SpringAPI.entity.Comics;

public interface ComicsRepository extends JpaRepository<Comics,Long> {

}
