package com.parking.administration.demo.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "brand", nullable = false, length = 70)
    private String brand;
    @Column(name = "model", nullable = false, length = 70)
    private String model;
    @Column(name = "color", nullable = false,length = 70)
    private String color;
    @Column(name = "license_plate_number", nullable = false, unique = true, length = 7)
    private String licensePlateNumber;
    @Column(name = "created_at", nullable = false, length = 70)
    private LocalDateTime createdAt = LocalDateTime.now();


    @ManyToOne
    @JoinColumn(name = "client_id")
    private User user;

    @OneToOne(mappedBy = "vehicle")
    private ParkingSpace parkingSpace;

    public Vehicle(String brand, String model, String color,  String licensePlateNumber) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.licensePlateNumber = licensePlateNumber;
    }

    public Vehicle() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", model='" + model + '\'' +
                ", licensePlateNumber='" + licensePlateNumber + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public static VehicleBuilder builder() {
        return new VehicleBuilder();
    }
    public static class VehicleBuilder {
        private Long id;
        private String brand;
        private String model;
        private String color;
        private String licensePlateNumber;
        private LocalDateTime createdAt;

        private VehicleBuilder() {
        }
        public VehicleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public VehicleBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }

        public VehicleBuilder model(String model) {
            this.model = model;
            return this;
        }

        public VehicleBuilder color(String color) {
            this.color = color;
            return this;
        }

        public VehicleBuilder licensePlateNumber(String licensePlateNumber) {
            this.licensePlateNumber = licensePlateNumber;
            return this;
        }

        public VehicleBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Vehicle build() {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(id);
            vehicle.setBrand(brand);
            vehicle.setModel(model);
            vehicle.setColor(color);
            vehicle.setLicensePlateNumber(licensePlateNumber);
            vehicle.setCreatedAt(createdAt);
            return vehicle;
        }
    }
}
