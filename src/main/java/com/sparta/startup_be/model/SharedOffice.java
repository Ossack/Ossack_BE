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

    private String personIncharge;
    private String number;
    private String agent;
    private String city;
    private String gu;
    private String dong;


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
        this.personIncharge = sharedOfficeDto.getPersonIncharge();
        this.number = sharedOfficeDto.getNumber();
        this.agent = sharedOfficeDto.getAgent();
        this.city = sharedOfficeDto.getCity();
        this.gu = sharedOfficeDto.getGu();
        this.dong = sharedOfficeDto.getDong();
    }
}
