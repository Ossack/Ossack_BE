package com.sparta.startup_be.coordinate.repository;

import com.sparta.startup_be.model.CoordinateEstate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoordinateEstateRepository extends JpaRepository<CoordinateEstate, Long> {
    CoordinateEstate findByEstateid(Long estateid);
}
