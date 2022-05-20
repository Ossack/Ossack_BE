package com.sparta.startup_be.model;

import com.sparta.startup_be.coordinate.dto.CoordinateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class CoordinateSharedOffice {
    @Id
    private Long sharedofficeid;
    private Double x;
    private Double y;

    public CoordinateSharedOffice(CoordinateDto coordinateDto){
        this.x = coordinateDto.getX();
        this.y = coordinateDto.getY();
        this.sharedofficeid = coordinateDto.getId();
    }
}
