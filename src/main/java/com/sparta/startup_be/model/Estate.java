package com.sparta.startup_be.model;

import com.sparta.startup_be.dto.EstateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(indexes={
        @Index( columnList ="gu"),
        @Index( columnList ="dong"),
        @Index( columnList ="id")
})
public class Estate {

    @Id
    private Long id;

    private String type;
    private String office;

    @Column
    private String city;
    private String gu;
    private String dong;

    @Column
    private String monthly;
    @Column
    private String area;

    private String buildingInfo;

    @Column
    private String deposit;
    @Column
    private String rent_fee;
    @Column
    private String buildingFloor;
    private String roomFloor;

    @ElementCollection
    @CollectionTable
    @JoinColumn
    private List<String> imageList;

    @ElementCollection
    @CollectionTable
    @JoinColumn
    private List<String> subwaylist;


    private String roadaddress;

    public Estate(EstateRequestDto estate){
        this.id = estate.getId();
        this.type = estate.getType();
        this.city = estate.getCity().split(" ")[0];
        this.gu = estate.getCity().split(" ")[1];
        this.dong = estate.getCity().split(" ")[2];
        this.area = estate.getArea();
        this.buildingFloor = estate.getBuildingFloor();
        this.buildingInfo= estate.getBuildingInfo();
        this.deposit = estate.getDeposit();
        this.rent_fee = estate.getRent_fee();
        this.roomFloor = estate.getRoomFloor();
        this.monthly = estate.getMonthly();
        this.imageList = estate.getImageList();
        this.subwaylist = estate.getSubwayList();
    }
}
