package com.carrental.autohire.dto;

//import lombok.AllArgsConstructor;
import lombok.Data;
//import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerResponseDto extends AbstractCustomerDto {
    private Long id;
    private Double totalPrice;

    // private List<? extends AbstractCarDto> carList;
    private List<CarResponseDto> carList;

    public CustomerResponseDto(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public CustomerResponseDto(Long customerId, String firstName, String lastName, String email, List<CarResponseDto> carResponseDtos) {
        super(firstName,lastName,email);
        id=customerId;
        carList = carResponseDtos;
    }

    public CustomerResponseDto( Long id,String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        this.id = id;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalPrice = totalExpense ;
    }
}
