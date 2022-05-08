package com.sparta.startup_be.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class MylikeDto {
    boolean mylike;

    public MylikeDto(boolean mylike){
        this.mylike = mylike;
    }
}