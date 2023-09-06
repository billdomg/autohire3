package com.carrental.autohire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CarRequestDto extends AbstractCarDto {
    private Boolean isBooked = Boolean.FALSE;
    private String manufacturer;
    private int year;
}
