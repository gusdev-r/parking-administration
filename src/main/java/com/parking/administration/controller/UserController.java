package com.parking.administration.controller;

import com.parking.administration.dto.request.VehiclePutRequest;
import com.parking.administration.dto.response.VehicleResponse;
import com.parking.administration.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parking.administration.util.Constants.BASE_URL;

@RestController
@RequestMapping(path = BASE_URL + "/users/")
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
    @GetMapping( "hello")
    public ResponseEntity<String> helloWorldUser () {
        return ResponseEntity.status(HttpStatus.OK).body("Hello World, user!");
    }
}
