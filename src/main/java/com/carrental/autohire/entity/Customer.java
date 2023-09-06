//package com.carrental.autohire.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.List;
//
//@Entity
//@Data
//public class Customer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String firstName;
//
//    private String lastName;
//
//    private String email;
//
//    @ManyToMany
//    @JoinTable(
//            name = "car_customer",
//            joinColumns = @JoinColumn(name = "customer_id"),
//            inverseJoinColumns = @JoinColumn(name = "car_id")
//    )
//    private List<Car> carList;
//}