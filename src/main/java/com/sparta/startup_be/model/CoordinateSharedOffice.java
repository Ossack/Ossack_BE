package com.sparta.startup_be.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class CoordinateSharedOffice {
    @Id
    private Long id;
    private Double x;
    private Double y;
}
