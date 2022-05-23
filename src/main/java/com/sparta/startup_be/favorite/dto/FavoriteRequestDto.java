package com.sparta.startup_be.favorite.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FavoriteRequestDto {
    private Long userid;
    private Long estateid;

    private String type;

    public FavoriteRequestDto(Long userid, Long estateid,String type){
        this.userid = userid;
        this.estateid = estateid;
<<<<<<< HEAD
        this.type = type;
=======
        this.type =type;
>>>>>>> 27e19519b6f92a5ab228847f5de427377ecaed43
    }
}
