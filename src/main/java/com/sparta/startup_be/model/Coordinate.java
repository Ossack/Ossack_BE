package com.sparta.startup_be.model;

import com.sparta.startup_be.dto.CoordinateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Coordinate {

    @Id
    private Long estateid;;

    private float x;
    private float y;

    public Coordinate(CoordinateDto coordinateDto){
        this.x = coordinateDto.getX();
        this.y = coordinateDto.getY();
        this.estateid = coordinateDto.getEstateid();
    }


}
