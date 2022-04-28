package com.sparta.startup_be.service;

import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.repository.EstateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstateService {
    private final EstateRepository estateRepository;

    public List<Estate> show(){
        return estateRepository.findAllByFloor(4);
    }
}
