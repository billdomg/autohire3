package com.carrental.autohire.controller;

import com.carrental.autohire.dto.BookVehicleDto;
import com.carrental.autohire.dto.CustomerResponseDto;
import com.carrental.autohire.exceptions.InvalidArgumentException;
import com.carrental.autohire.service.CustomerService;
import com.carrental.autohire.util.CustomerRequestValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "bookvehicle")
    public ResponseEntity<CustomerResponseDto> bookcar(@RequestBody BookVehicleDto bookVehicleDto) throws IllegalAccessException {
        if (!CustomerRequestValidator.isValidCustomerRequestDto(bookVehicleDto)) {
           throw new InvalidArgumentException("Please check , email , firstname , lastname and email should be in proper format");
        }
        CustomerResponseDto createdCustomer = customerService.bookCar(bookVehicleDto);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long customerId) {
        CustomerResponseDto customer = customerService.getCustomerById(customerId);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

