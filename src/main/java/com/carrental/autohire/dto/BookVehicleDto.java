package com.carrental.autohire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVehicleDto {
    private String firstName;
    private String lastName;
    private String email;
    private Long carId;
}
