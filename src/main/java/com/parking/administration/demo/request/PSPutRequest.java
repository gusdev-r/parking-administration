package com.parking.administration.demo.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PSPutRequest {
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
