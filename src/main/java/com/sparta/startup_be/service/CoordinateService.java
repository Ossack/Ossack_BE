package com.sparta.startup_be.service;

import com.sparta.startup_be.dto.CoordinateDto;
import com.sparta.startup_be.model.Coordinate;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.repository.CoordinateRepository;
import com.sparta.startup_be.repository.EstateRepository;
import com.sparta.startup_be.utils.ConvertAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinateService {
    private final CoordinateRepository coordinateRepository;
    private final EstateRepository estateRepository;
    private final ConvertAddress convertAddress;

    public void convertAddress() {
        List<Estate> estateList = estateRepository.findAll();
        for (Estate estate : estateList) {
            String address = estate.getDong();
            String response = convertAddress.convertAddress(address);
            CoordinateDto coordinateDto = convertAddress.fromJSONtoItems(response, estate.getId());
            Coordinate coordinate = new Coordinate(coordinateDto);
            coordinateRepository.save(coordinate);
        }
    }

    public void storeAddress(List<Estate> estateList) {
        for (Estate estate : estateList) {
            String address = estate.getDong();
            String response = convertAddress.convertAddress(address);
            CoordinateDto coordinateDto = convertAddress.fromJSONtoItems(response, estate.getId());
            Coordinate coordinate = new Coordinate(coordinateDto);
            coordinateRepository.save(coordinate);
        }
    }
}


