package com.sparta.startup_be.sharedOffice.dto;

import com.sparta.startup_be.estate.dto.EstateResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchSharedOfficeResponseDto {
    private List<SharedOfficeResponseDto> sharedOfficeResponseDtos;
    private int totalpage;
    private int presentpage;


    public SearchSharedOfficeResponseDto (List<SharedOfficeResponseDto> sharedOfficeResponseDtos , int totalpage,int presentpage) {
        this.sharedOfficeResponseDtos = sharedOfficeResponseDtos;
        this.totalpage = totalpage;
        this.presentpage = presentpage;
    }
}
