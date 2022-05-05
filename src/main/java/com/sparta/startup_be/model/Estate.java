package com.sparta.startup_be.model;

import com.sparta.startup_be.dto.EstateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes= @Index( columnList ="city,monthly"))
public class Estate {

    @Id
    private Long id;

    private String type;

    @Column
    private String city;

    @Column
    private String monthly;

    @Column
    private String area;

    private String buildingInfo;

    @Column
    private String deposit;
    @Column
    private int rent_fee;
    @Column
    private int buildingFloor;
    private int roomFloor;

    @ElementCollection
    @CollectionTable
    @JoinColumn
    private List<String> imageList;

    @ElementCollection
    @CollectionTable
    @JoinColumn
    private List<String> subwaylist;


    private String roadaddress;

    public Estate(EstateRequestDto estateRequestDto){
        this.id = estateRequestDto.getId();
        this.type = estateRequestDto.getType();
        this.city = estateRequestDto.getCity();
        this.area = estateRequestDto.getArea();
        this.buildingFloor = estateRequestDto.getBuildingFloor();
        this.buildingInfo= estateRequestDto.getBuildingInfo();
        this.deposit = estateRequestDto.getDeposit();
        this.rent_fee = estateRequestDto.getRent_fee();
        this.roomFloor = estateRequestDto.getRoomFloor();
        this.monthly = estateRequestDto.getMonthly();
        this.imageList = estateRequestDto.getImageList();
        this.subwaylist = estateRequestDto.getSubwayList();
    }
}
