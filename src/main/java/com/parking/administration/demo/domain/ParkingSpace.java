package com.parking.administration.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_parking_spaces")
public class ParkingSpace implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public ParkingSpace() {
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public String getCondominiumBlock() {
        return condominiumBlock;
    }

    public void setCondominiumBlock(String condominiumBlock) {
        this.condominiumBlock = condominiumBlock;
    }

    public String getCondominiumApartment() {
        return condominiumApartment;
    }

    public void setCondominiumApartment(String condominiumApartment) {
        this.condominiumApartment = condominiumApartment;
    }

    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsibleVehicleName() {
        return responsibleVehicleName;
    }

    public void setResponsibleVehicleName(String responsibleVehicleName) {
        this.responsibleVehicleName = responsibleVehicleName;
    }

    public String getVehicleSpaceNumber() {
        return vehicleSpaceNumber;
    }

    public void setVehicleSpaceNumber(String vehicleSpaceNumber) {
        this.vehicleSpaceNumber = vehicleSpaceNumber;
    }

    public String getVehicleLicensePlateNumber() {
        return vehicleLicensePlateNumber;
    }

    public void setVehicleLicensePlateNumber(String vehicleLicensePlateNumber) {
        this.vehicleLicensePlateNumber = vehicleLicensePlateNumber;
    }
    public ParkingSpace(String condominiumBlock, String condominiumApartment, String vehicleBrand, String vehicleModel,
                        String vehicleColor, LocalDateTime createdAt, Long id, String responsibleVehicleName,
                        String vehicleSpaceNumber, String vehicleLicensePlateNumber) {
        this.condominiumBlock = condominiumBlock;
        this.condominiumApartment = condominiumApartment;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.vehicleColor = vehicleColor;
        this.createdAt = createdAt;
        this.id = id;
        this.responsibleVehicleName = responsibleVehicleName;
        this.vehicleSpaceNumber = vehicleSpaceNumber;
        this.vehicleLicensePlateNumber = vehicleLicensePlateNumber;
    }


    public static ParkingSpaceBuilder builder() {
        return new ParkingSpaceBuilder();
    }

    public static class ParkingSpaceBuilder {
        private String condominiumBlock;
        private String condominiumApartment;
        private String vehicleBrand;
        private String vehicleModel;
        private String vehicleColor;
        private LocalDateTime createdAt = LocalDateTime.now();
        private Long id;
        private String responsibleVehicleName;
        private String vehicleSpaceNumber;
        private String vehicleLicensePlateNumber;

        private ParkingSpaceBuilder() {

        }

        public ParkingSpaceBuilder condominiumBlock(String condominiumBlock) {
            this.condominiumBlock = condominiumBlock;
            return this;
        }

        public ParkingSpaceBuilder condominiumApartment(String condominiumApartment) {
            this.condominiumApartment = condominiumApartment;
            return this;
        }

        public ParkingSpaceBuilder vehicleBrand(String vehicleBrand) {
            this.vehicleBrand = vehicleBrand;
            return this;
        }

        public ParkingSpaceBuilder vehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
            return this;
        }

        public ParkingSpaceBuilder vehicleColor(String vehicleColor) {
            this.vehicleColor = vehicleColor;
            return this;
        }

        public ParkingSpaceBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ParkingSpaceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ParkingSpaceBuilder responsibleVehicleName(String responsibleVehicleName) {
            this.responsibleVehicleName = responsibleVehicleName;
            return this;
        }

        public ParkingSpaceBuilder vehicleSpaceNumber(String vehicleSpaceNumber) {
            this.vehicleSpaceNumber = vehicleSpaceNumber;
            return this;
        }

        public ParkingSpaceBuilder vehicleLicensePlateNumber(String vehicleLicensePlateNumber) {
            this.vehicleLicensePlateNumber = vehicleLicensePlateNumber;
            return this;
        }

        public ParkingSpace build() {
            return new ParkingSpace(condominiumBlock, condominiumApartment, vehicleBrand, vehicleModel,
                    vehicleColor, createdAt, id, responsibleVehicleName, vehicleSpaceNumber, vehicleLicensePlateNumber);
        }
    }
}
