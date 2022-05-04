//package com.sparta.startup_be.model;
//
//import com.sparta.startup_be.dto.ImageDto;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class Image {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column
//    private Long id;
//
//    @Column
//    private String imageUrl;
//
//    public Image(ImageDto imageDto){
//        this.imageUrl = imageDto.getImageUrl();
//    }
//
//}
