package com.sparta.startup_be.model;

import com.sparta.startup_be.coordinate.dto.CoordinateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class CoordinateEstate {

    @Id
    private Long estateid;;

    private double x;
    private double y;

    public CoordinateEstate(CoordinateDto coordinateDto){
        this.x = coordinateDto.getX();
        this.y = coordinateDto.getY();
        this.estateid = coordinateDto.getEstateid();
    }


}
