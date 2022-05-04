package com.sparta.startup_be.repository;

import com.sparta.startup_be.model.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
    List<Coordinate> findAllByXGreaterThanAndXLessThanAndYGreaterThanAndYLessThan(Float minX, Float maxX, Float minY, Float maxY );
}
