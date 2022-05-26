package com.sparta.startup_be.estate.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchDto {

    private List<EstateResponseDto> estateResponseDtoList;
    private int totalpage;
    private int presentpage;

    private String keyword;


    public SearchDto (List<EstateResponseDto> estateResponseDtoList , int totalpage,int presentpage,String keyword) {
        this.estateResponseDtoList = estateResponseDtoList;
        this.totalpage = totalpage;
        this.presentpage = presentpage;
        this.keyword = keyword;
    }
}
