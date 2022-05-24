package com.sparta.startup_be.favorite.dto;


import com.sparta.startup_be.model.Estate;
import lombok.Getter;

import java.util.List;

@Getter
public class FavoriteListDto {
    private Long id;
    private String title;
    private String buildingFloor;
    private String roomFloor;
    private String type;
    private String monthly;
    private int deposit;
    private int rent_fee;
    private List<String> imageUrl;
    private boolean mylike;

    public FavoriteListDto(Estate estate ,boolean mylike) {
        this.id = estate.getId();
        this.title = estate.getCity();
        this.buildingFloor = estate.getBuildingFloor();
        this.roomFloor = estate.getRoomFloor();
        this.type = estate.getType();
        this.monthly = estate.getMonthly();
        this.deposit = estate.getDeposit();
        this.rent_fee = estate.getRent_fee();
        this.imageUrl = estate.getImageList();
        this.mylike = mylike;

    }
}
