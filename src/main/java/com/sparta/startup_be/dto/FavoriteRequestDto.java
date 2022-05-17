package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequestDto {
    private Long userid;
    private Long estateid;

    public FavoriteRequestDto(Long userid, Long estateid){
        this.userid = userid;
        this.estateid = estateid;
    }
}
