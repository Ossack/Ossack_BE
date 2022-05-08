package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDto {
    private Long userid;
    private Long estateid;

    public FavoriteDto(Long userid, Long estateid){
        this.userid = userid;
        this.estateid = estateid;
    }
}
