package com.sparta.startup_be.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddressController {

    @GetMapping("/address")
    public String address(){
        System.out.println("다빈님 바보");
        return "address";
    }

    @GetMapping("/marker")
    public String marker(){
        System.out.println("다빈님 바보");
        return "marker";
    }
}
