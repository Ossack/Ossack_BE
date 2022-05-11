package com.sparta.startup_be.dto;

import com.sparta.startup_be.model.Estate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchDto {

    private List<EstateResponseDto> estateResponseDtoList;
    private int totalpage;

    public SearchDto (List<EstateResponseDto> estateResponseDtoList , int totalpage) {
        this.estateResponseDtoList = estateResponseDtoList;
        this.totalpage = totalpage;
    }

}
