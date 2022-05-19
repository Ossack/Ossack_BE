package com.sparta.startup_be.service;

import com.sparta.startup_be.model.Estate;
import com.sparta.startup_be.model.SharedOffice;
import com.sparta.startup_be.repository.SharedOfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharedOfficeService {
    private final SharedOfficeRepository sharedOfficeRepository;
    public void storeSharedOffice(List<SharedOffice> sharedOffices) {
        for (SharedOffice sharedOffice : sharedOffices) {
            sharedOfficeRepository.save(sharedOffice);
        }
    }
}
