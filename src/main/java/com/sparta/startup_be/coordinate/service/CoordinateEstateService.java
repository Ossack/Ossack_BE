package com.sparta.startup_be.coordinate.service;

import com.sparta.startup_be.coordinate.repository.CoordinateEstateRepository;
import com.sparta.startup_be.coordinate.dto.CoordinateDto;
import com.sparta.startup_be.model.CoordinateEstate;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.estate.EstateRepository;
import com.sparta.startup_be.utils.ConvertAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinateEstateService {
    private final CoordinateEstateRepository coordinateEstateRepository;
    private final EstateRepository estateRepository;
    private final ConvertAddress convertAddress;

    public void convertAddress() {
        List<Estate> estateList = estateRepository.findAll();
        for (Estate estate : estateList) {
            String address = estate.getDong();
            String response = convertAddress.convertAddress(address);
            CoordinateDto coordinateDto = convertAddress.fromJSONtoItems(response, estate.getId());
            CoordinateEstate coordinateEstate = new CoordinateEstate(coordinateDto);
            coordinateEstateRepository.save(coordinateEstate);
        }
    }

    public void storeAddress(List<Estate> estateList) {
        for (Estate estate : estateList) {
            String address = estate.getDong();
            String response = convertAddress.convertAddress(address);
            CoordinateDto coordinateDto = convertAddress.fromJSONtoItems(response, estate.getId());
            CoordinateEstate coordinateEstate = new CoordinateEstate(coordinateDto);
            coordinateEstateRepository.save(coordinateEstate);
        }
    }
}


