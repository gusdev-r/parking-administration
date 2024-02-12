package com.parking.administration.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.parking.administration.demo.domain.ParkingSpace;

import java.util.UUID;
@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, UUID> {

    boolean existsByLicensePlateVehicle(String licensePlateVehicle);
    boolean existsByVehicleSpaceNumber(String vehicleSpaceNumber);
    boolean existsByApartmentAndBlock(String apartment, String block);
}
