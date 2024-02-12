package com.parking.administration.demo.customValidator;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.parking.administration.demo.domain.ParkingSpace;
import com.parking.administration.demo.request.PSPostRequest;
import com.parking.administration.demo.service.ParkingSpaceService;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
public class PSValidator  {

    private final ParkingSpaceService parkingSpaceService;

    public PSValidator(ParkingSpaceService parkingSpaceService) {
        this.parkingSpaceService = parkingSpaceService;
    }

    public Object validateRegister(PSPostRequest parkingSpacePostRequest) {
        if (parkingSpaceService.existsByLicensePlateVehicle(parkingSpacePostRequest.getVehicleLicensePlateNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: The license plate of this vehicle is already activated in use.");
        }
        if (parkingSpaceService.existsByVehicleSpaceNumber(parkingSpacePostRequest.getVehicleLicensePlateNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This vehicle space number is already activated in use.");
        }
        if (parkingSpaceService.existsByApartmentAndBlock(parkingSpacePostRequest.getCondominiumApartment(), parkingSpacePostRequest.getCondominiumBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: This parking space is already registered in an apartment/block.");
        }
        return null;
    }
    public Object validateParkingSpaceById(UUID id) {
        Optional<ParkingSpace> psOptional = parkingSpaceService.findById(id);
        if (!psOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Parking Space not found.");
        }
        return psOptional;
    }
    public Object validateUpdateParkingSpace(UUID id) {
        Optional<ParkingSpace> psOptional = parkingSpaceService.findById(id);
        if (!psOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Parking Space not found.");
        }
        return null;
    }
    public ResponseEntity<Object> validateDeleteParkingSpaceById(UUID id) {
        Optional<ParkingSpace> psOptional = parkingSpaceService.findById(id);
        if (!psOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Parking Space not found to delete.");
        }
        parkingSpaceService.delete(psOptional.get());
        return null;
    }

}
