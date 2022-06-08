package com.sparta.startup_be.crawling;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JmeterController {

    @GetMapping("/jmeter")
    public String jmeter(){
        return "성공";
    }
}
