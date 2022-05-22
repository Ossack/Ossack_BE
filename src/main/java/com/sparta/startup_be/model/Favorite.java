package com.sparta.startup_be.model;


import com.sparta.startup_be.favorite.dto.FavoriteRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Entity
@NoArgsConstructor
@IdClass(FavoriteId.class)
public class Favorite {
    @Id
    @Column
    private Long userid;

    @Id
    @Column
    private Long estateid;

    public Favorite(FavoriteRequestDto favoriteRequestDto){
        this.estateid= favoriteRequestDto.getEstateid();
        this.userid = favoriteRequestDto.getUserid();
    }
}
