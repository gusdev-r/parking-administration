package com.parking.administration.demo.controller;

import com.parking.administration.demo.dto.response.VehicleResponse;
import com.parking.administration.demo.mapper.VehicleMapper;
import com.parking.administration.demo.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parking.administration.demo.utils.Utility.LOGGER;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/v1/vehicle")
public class VehicleController {
    private VehicleService vehicleService;
    private VehicleMapper vehicleMapper;
    public VehicleController(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    public VehicleController() {
    }

    @GetMapping(path = "/show/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        LOGGER.info("Request receive to find a vehicle by param Id '{}' - VehicleController", id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleMapper.toVehicleResponse(vehicleService.findById(id)));
    }

    //verify the possibility to use this one only the permission of user moder, then the client can't access it
    @GetMapping(path = "/show/{licensePlateNumber}")
    public ResponseEntity<VehicleResponse> getVehicleByLicensePlateNumber(@RequestBody String licensePlateNumber) {
        LOGGER.info("Request receive to find a vehicle by param license plate number '{}' - VehicleController",
                licensePlateNumber);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleMapper
                .toVehicleResponse(vehicleService.findByLicensePlateNumber(licensePlateNumber)));
    }
    @GetMapping(path = "/show")
    private ResponseEntity<List<VehicleResponse>> getAllVehiclesRegistered() {
        LOGGER.info("Request receive to list all vehicles available - VehicleController");
        return ResponseEntity.status(HttpStatus.OK).body(vehicleMapper.toListOfVehicle(vehicleService.findAll()));
    }

}