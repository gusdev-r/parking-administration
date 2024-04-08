package com.parking.administration.demo.controller;

import com.parking.administration.demo.dto.request.VehiclePutRequest;
import com.parking.administration.demo.dto.response.VehicleResponse;
import com.parking.administration.demo.mapper.VehicleMapper;
import com.parking.administration.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = {"/v1/api/user", "/v1/api/user/"})
public class UserController {

    private VehicleMapper vehicleMapper;
    private UserService userService;


    @GetMapping("vehicle/list/{userId}")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(@PathVariable Long userId) {
        var vehicleListResponse = vehicleMapper.toListOfVehicle(userService.findUserById(userId).getVehicleList());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleListResponse);
    }
    @DeleteMapping("{userId}/{vehicleId}")
    public void deleteVehicleById (@PathVariable Long userId ,@PathVariable Long vehicleId) {
        userService.deleteVehicleRegistered(userId, vehicleId);
    }
    @PutMapping("/{userId}/{vehicleId}")
    public void updateVehicle(@RequestBody VehiclePutRequest vehiclePutRequest,
                              @PathVariable Long userId , @PathVariable Long vehicleId) {
        var vehicleToUpdate = vehicleMapper.toVehiclePutRequest(vehiclePutRequest);
        userService.updateVehicleAttributes(vehicleToUpdate, vehicleId, userId);
    }
}
