package com.carrental.autohire.controller;

import com.carrental.autohire.dto.AbstractCarDto;
import com.carrental.autohire.dto.CarRequestDto;
import com.carrental.autohire.dto.CarResponseDto;
import com.carrental.autohire.exceptions.InvalidArgumentException;
import com.carrental.autohire.service.CarService;
import com.carrental.autohire.util.CarRequestValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private CarService carService;

    @PostMapping(value = "addcar")
    public ResponseEntity<? extends AbstractCarDto> addCar(@RequestBody CarRequestDto carRequestDto) throws IllegalAccessException {
        if (!CarRequestValidator.isValidCarRequestDto(carRequestDto)) {
            throw new InvalidArgumentException("Invalid car details");
        }
        CarResponseDto createdCar = carService.addCar(carRequestDto);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }
}
