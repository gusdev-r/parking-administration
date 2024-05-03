package com.parking.administration.controller;

import com.parking.administration.dto.response.VehicleResponse;
import com.parking.administration.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parking.administration.util.Utility.LOGGER;

@RequestMapping(path = "/v1/api/vehicle")
@RequiredArgsConstructor
@RestController
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping(path = "/show/id/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        LOGGER.info("Request receive to find a vehicle by param Id '{}' - VehicleController", id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findById(id));
    }
    @GetMapping(path = "/show/license")
    public ResponseEntity<VehicleResponse> getVehicleByLicensePlateNumber(@RequestBody String licensePlateNumber) {
        LOGGER.info("Request receive to find a vehicle by param license plate number '{}' - VehicleController",
                licensePlateNumber);
        return ResponseEntity.status(HttpStatus.OK).body((vehicleService.findByLicensePlateNumber(licensePlateNumber)));
    }
    @GetMapping(path = "/show/all")
    public ResponseEntity<List<VehicleResponse>> getAllVehiclesRegistered() {
        LOGGER.info("Request receive to list all vehicles available - VehicleController");
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findAll());
    }
}