//package com.carrental.autohire.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Entity
//@Data
//public class Car {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String manufacturer;
//
//    private String model;
//
//    private int year;
//
//    private String color;
//
//    private Long price;
//
//    private Boolean isBooked;
//
//    @ManyToMany(mappedBy = "carList")
//    private List<Customer> customers;
//
//}