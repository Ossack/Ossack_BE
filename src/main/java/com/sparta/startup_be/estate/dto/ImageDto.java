package com.sparta.startup_be.estate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {
    private String imageUrl;

    public ImageDto(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
