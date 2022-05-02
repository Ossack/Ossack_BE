package com.sparta.startup_be.model;

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

    @Column
    private String city;

    @Column
    private String name;

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


}
