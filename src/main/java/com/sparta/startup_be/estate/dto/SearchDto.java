package com.sparta.startup_be.estate.dto;

import com.sparta.startup_be.estate.dto.EstateResponseDto;
import com.sparta.startup_be.sharedOffice.dto.SharedOfficeResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchDto {

    private List<EstateResponseDto> estateResponseDtoList;
    private int totalpage;
    private int presentpage;


    public SearchDto (List<EstateResponseDto> estateResponseDtoList , int totalpage,int presentpage) {
        this.estateResponseDtoList = estateResponseDtoList;
        this.totalpage = totalpage;
        this.presentpage = presentpage;
    }



}
