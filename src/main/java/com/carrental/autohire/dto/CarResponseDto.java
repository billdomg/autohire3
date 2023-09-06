package com.carrental.autohire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CarResponseDto extends AbstractCarDto {

    private Long id;
    private Boolean isBooked;

    public CarResponseDto(Long carId, String manufacturer, String model, int year, String color,Long price) {
        super(manufacturer,model,year,color,price);
        this.id = carId;
    }
}
