package com.sparta.startup_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Estate {

    @Id
    private Long id;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String name;

    @Column
    private String monthly;

    @Column
    private double area;

    @Column
    private String contract_year;

    @Column
    private String deposit;
    @Column
    private int rent_fee;
    @Column
    private int floor;
    @Column
    private String construct_year;
    @Column
    private String roadaddress;


}
