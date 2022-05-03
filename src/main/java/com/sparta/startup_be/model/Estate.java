package com.sparta.startup_be.model;

import com.sparta.startup_be.dto.EstateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
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

    private String roadaddress;

    public Estate(EstateDto estateDto){
        this.id = estateDto.getId();
        this.type = estateDto.getType();
        this.city = estateDto.getCity();
        this.area = estateDto.getArea();
        this.buildingFloor = estateDto.getBuildingFloor();
        this.buildingInfo= estateDto.getBuildingInfo();
        this.deposit = estateDto.getDeposit();
        this.rent_fee = estateDto.getRent_fee();
        this.roomFloor = estateDto.getRoomFloor();
        this.monthly = estateDto.getMonthly();
    }
}
