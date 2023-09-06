package com.carrental.autohire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractCarDto {

    private String manufacturer;
    private String model;
    private int year;
    private String color;

    private Long price;
}
