package com.sparta.startup_be.coordinate.service;

import com.sparta.startup_be.coordinate.dto.CoordinateDto;
import com.sparta.startup_be.coordinate.repository.CoordinateSharedOfficeRepository;
import com.sparta.startup_be.model.CoordinateEstate;
import com.sparta.startup_be.model.CoordinateSharedOffice;
import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.SharedOffice;
import com.sparta.startup_be.sharedOffice.SharedOfficeRepository;
import com.sparta.startup_be.utils.ConvertAddress;
import com.sparta.startup_be.utils.NaverSearchApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinateSharedOfficeService {
    private final SharedOfficeRepository sharedOfficeRepository;
    private final ConvertAddress convertAddress;
    private final CoordinateSharedOfficeRepository coordinateSharedOfficeRepository;
    private final NaverSearchApi naverSearchApi;
    public void convertAddress() throws InterruptedException {
        List<SharedOffice> sharedOffices = sharedOfficeRepository.findAll();
        for (SharedOffice sharedOffice : sharedOffices) {
            String name = sharedOffice.getName();
            CoordinateDto coordinateDto = new CoordinateDto(naverSearchApi.getCoordinate(name),sharedOffice.getId());
            CoordinateSharedOffice coordinateSharedOffice = new CoordinateSharedOffice(coordinateDto);
            coordinateSharedOfficeRepository.save(coordinateSharedOffice);
        }
    }

    public void storeAddress(List<SharedOffice> sharedOffices) {
        for (SharedOffice sharedOffice : sharedOffices) {
            String address = sharedOffice.getDong();
            String response = convertAddress.convertAddress(address);
            CoordinateDto coordinateDto = convertAddress.fromJSONtoItems(response, sharedOffice.getId());
            CoordinateSharedOffice coordinateSharedOffice = new CoordinateSharedOffice(coordinateDto);
            coordinateSharedOfficeRepository.save(coordinateSharedOffice);
        }
    }
}
