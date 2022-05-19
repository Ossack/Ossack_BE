package com.sparta.startup_be.sharedOffice;

import com.sparta.startup_be.model.SharedOffice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SharedOfficeController {
    private final SharedOfficeService sharedOfficeService;

//    @GetMapping("/map/sharedoffices/{level}")
//    public List
}
