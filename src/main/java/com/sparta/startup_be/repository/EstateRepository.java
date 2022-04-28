package com.sparta.startup_be.repository;

import com.sparta.startup_be.model.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstateRepository extends JpaRepository<Estate, Integer> {
    List<Estate> findAllByFloor(int floor);
}
