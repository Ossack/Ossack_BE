package com.sparta.startup_be.sharedOffice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchSharedOfficeResponseDto {
    private List<SharedOfficeResponseDto> sharedOfficeResponseDtos;
    private int totalpage;
    private int presentpage;

    private String keyword;


    public SearchSharedOfficeResponseDto (List<SharedOfficeResponseDto> sharedOfficeResponseDtos , int totalpage,int presentpage,String keyword) {
        this.sharedOfficeResponseDtos = sharedOfficeResponseDtos;
        this.totalpage = totalpage;
        this.presentpage = presentpage;
        this.keyword = keyword;
    }
}
