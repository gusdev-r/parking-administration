package com.parking.administration.controller;

import com.parking.administration.dto.request.VehiclePutRequest;
import com.parking.administration.dto.response.VehicleResponse;
import com.parking.administration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/v1/api/user", "/v1/api/user/"})
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("vehicle/list/{userId}")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllVehiclesFromUser(userId));
    }

    @DeleteMapping("{userId}/{vehicleId}")
    public void deleteVehicleById (@PathVariable Long userId ,@PathVariable Long vehicleId) {
        userService.deleteVehicleRegistered(userId, vehicleId);
    }
    @PutMapping("/{userId}/{vehicleId}")
    public void updateVehicle(@RequestBody VehiclePutRequest vehiclePutRequest,
                              @PathVariable Long userId , @PathVariable Long vehicleId) {
        userService.updateVehicleAttributes(vehiclePutRequest, vehicleId, userId);
    }
}
