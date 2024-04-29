package com.parking.administration.controller;

import com.parking.administration.dto.request.VehiclePutRequest;
import com.parking.administration.dto.response.VehicleResponse;
import com.parking.administration.mapper.VehicleMapper;
import com.parking.administration.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = {"/v1/api/user", "/v1/api/user/"})
@RequiredArgsConstructor
public class UserController {

    private final VehicleMapper mapper;
    private final UserDetailsService userDetailsService;

    @GetMapping("vehicle/list/{userId}")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles(@PathVariable Long userId) {
        var vehicleListResponse = mapper.toListOfVehicle(userDetailsService.findUserById(userId).getVehicleList());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleListResponse);
    }
    @DeleteMapping("{userId}/{vehicleId}")
    public void deleteVehicleById (@PathVariable Long userId ,@PathVariable Long vehicleId) {
        userDetailsService.deleteVehicleRegistered(userId, vehicleId);
    }
    @PutMapping("/{userId}/{vehicleId}")
    public void updateVehicle(@RequestBody VehiclePutRequest vehiclePutRequest,
                              @PathVariable Long userId , @PathVariable Long vehicleId) {
        var vehicleToUpdate = mapper.toVehiclePutRequest(vehiclePutRequest);
        userDetailsService.updateVehicleAttributes(vehicleToUpdate, vehicleId, userId);
    }
}
