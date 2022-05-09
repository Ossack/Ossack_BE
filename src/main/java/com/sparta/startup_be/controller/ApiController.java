package com.sparta.startup_be.controller;

import com.sparta.startup_be.dto.ApiDto;
import com.sparta.startup_be.utils.SearchApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
@ApiIgnore
@RestController
@RequiredArgsConstructor
public class ApiController {
    private final SearchApi searchApi;
    @GetMapping("/api/search")
    public List<ApiDto> getItems(){
        String resultString = searchApi.searchApi();
        return searchApi.fromJSONtoItems(resultString);
    }
}
