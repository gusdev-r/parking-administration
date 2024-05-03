package com.parking.administration.repository;

import com.parking.administration.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByLicensePlateNumber(String licensePlateNumber);
}
