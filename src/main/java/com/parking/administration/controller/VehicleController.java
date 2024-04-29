package com.parking.administration.controller;

import com.parking.administration.dto.response.VehicleResponse;
import com.parking.administration.mapper.VehicleMapper;
import com.parking.administration.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parking.administration.util.Utility.LOGGER;

@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/v1/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @GetMapping(path = "/show/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        LOGGER.info("Request receive to find a vehicle by param Id '{}' - VehicleController", id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleMapper.toVehicleResponse(vehicleService.findById(id)));
    }
    @GetMapping(path = "/show")
    public ResponseEntity<VehicleResponse> getVehicleByLicensePlateNumber(@RequestBody String licensePlateNumber) {
        LOGGER.info("Request receive to find a vehicle by param license plate number '{}' - VehicleController",
                licensePlateNumber);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleMapper
                .toVehicleResponse(vehicleService.findByLicensePlateNumber(licensePlateNumber)));
    }
    @GetMapping(path = "/show")
    public ResponseEntity<List<VehicleResponse>> getAllVehiclesRegistered() {
        LOGGER.info("Request receive to list all vehicles available - VehicleController");
        return ResponseEntity.status(HttpStatus.OK).body(vehicleMapper.toListOfVehicle(vehicleService.findAll()));
    }
}