package com.parking.administration.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

}
