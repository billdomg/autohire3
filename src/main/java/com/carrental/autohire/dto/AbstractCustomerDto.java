package com.carrental.autohire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractCustomerDto {
    private String firstName;
    private String lastName;
    private String email;


}
