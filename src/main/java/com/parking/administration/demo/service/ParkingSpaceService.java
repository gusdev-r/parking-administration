package com.parking.administration.demo.service;

import com.parking.administration.demo.domain.ParkingSpace;
import com.parking.administration.demo.repository.ParkingSpaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ParkingSpaceService implements PSService{
    final ParkingSpaceRepository psRepository;

    public ParkingSpaceService(ParkingSpaceRepository parkingSpaceRepository) {
        this.psRepository = parkingSpaceRepository;
    }

    @Transactional
    public ParkingSpace save(ParkingSpace parkingSpace) {
        return psRepository.save(parkingSpace);
    }

    public boolean existsByLicensePlateVehicle (String licensePlateNumber) {
        return psRepository.existsByLicensePlateVehicle(licensePlateNumber);
    }
    public boolean existsByVehicleSpaceNumber (String vehicleSpaceNumber) {
        return psRepository.existsByVehicleSpaceNumber(vehicleSpaceNumber);
    }
    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return psRepository.existsByApartmentAndBlock(apartment, block);
    }
    public Page<ParkingSpace> findAll(Pageable pageable) {
        return psRepository.findAll(pageable);
    }
    public List<ParkingSpace> findAllWithMapper() {
        return psRepository.findAll();
    }
    public Optional<ParkingSpace> findById(UUID id) {
        return psRepository.findById(id);
    }
    @Transactional
    public void delete(ParkingSpace parkingSpace) {
        psRepository.delete(parkingSpace);
    }
}
