package com.sparta.startup_be.model;


import com.sparta.startup_be.dto.FavoriteDto;
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

    public Favorite(FavoriteDto favoriteDto){
        this.estateid= favoriteDto.getEstateid();
        this.userid = favoriteDto.getUserid();
    }
}
