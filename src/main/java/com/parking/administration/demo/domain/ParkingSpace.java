package com.parking.administration.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PARKING_SPACE")
@Getter
@Setter
public class ParkingSpace implements Serializable { // do a relation one-to-one with the vehicle clas
    private static final long serialVersionUID = 1L; // con

    @Column(nullable = false, length = 40)
    private String condominiumBlock;

    @Column(nullable = false, length = 40)
    private String condominiumApartment;

    @Column(nullable = false, length = 70)
    private String vehicleBrand;

    @Column(nullable = false, length = 70)
    private String vehicleModel;

    @Column(nullable = false, length = 70)
    private String vehicleColor;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 140)
    private String responsibleVehicleName;

    @Column(nullable = false, unique = true, length = 10)
    private String vehicleSpaceNumber;

    @Column(nullable = false, unique = true, length = 7)
    @Size(max = 7)
    private String vehicleLicensePlateNumber;

    @Column(nullable = false)
    private LocalDateTime registrationDate;
}
