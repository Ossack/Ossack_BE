package com.sparta.startup_be.model;

import com.sparta.startup_be.sharedOffice.dto.SharedOfficeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class SharedOffice extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String name;
    private String subwayInfo;
    private String price;
    private String time;
    private String minimum_days;
    private String floor;
    private String parking;
    @Column(length = 1000)
    private String detail;

    @ElementCollection
    @CollectionTable
    @JoinColumn
    private List<String> convience;
    @ElementCollection
    @CollectionTable
    @JoinColumn
    private List<String> imageList;


    public SharedOffice(SharedOfficeDto sharedOfficeDto){
        this.name = sharedOfficeDto.getName();
        this.subwayInfo = sharedOfficeDto.getSubwayInfo();
        this.price =sharedOfficeDto.getPrice();
        this.time = sharedOfficeDto.getTime();
        this.minimum_days = sharedOfficeDto.getMinimum_days();
        this.floor = sharedOfficeDto.getFloor();
        this.parking = sharedOfficeDto.getParking();
        this.detail = sharedOfficeDto.getDetail();
        this.imageList = sharedOfficeDto.getImageList();
        this.convience =sharedOfficeDto.getConvience();
    }
}
