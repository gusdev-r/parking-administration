package com.parking.administration.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuperBuilder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_parking_spaces")
public class ParkingSpace implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "condominium_block", nullable = false, length = 40)
    private String condominiumBlock;

    @Column(name = "condominium_apartment",nullable = false, length = 40)
    private String condominiumApartment;

    @Column(name = "vehicle_brand",nullable = false, length = 70)
    private String vehicleBrand;

    @Column(name = "vehicle_model",nullable = false, length = 70)
    private String vehicleModel;

    @Column(name = "vehicle_color",nullable = false, length = 70)
    private String vehicleColor;

    @Column(name = "responsible_vehicle_name",nullable = false, length = 140)
    private String responsibleVehicleName;

    @Column(name = "vehicle_space_number",nullable = false, unique = true, length = 10)
    private String vehicleSpaceNumber;

    @Column(name = "vehicle_license_plate_number",nullable = false, unique = true, length = 7)
    @Size(max = 7)
    private String vehicleLicensePlateNumber;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;
}
