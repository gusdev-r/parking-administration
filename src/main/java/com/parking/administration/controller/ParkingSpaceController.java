package com.parking.administration.controller;

import com.parking.administration.dto.request.ParkingSpacePutRequest;
import com.parking.administration.dto.request.ParkingSpaceRequestPost;
import com.parking.administration.dto.response.ParkingSpaceResponse;
import com.parking.administration.infra.exception.BadRequestException;
import com.parking.administration.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.service.ParkingSpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parking.administration.util.Utility.LOGGER;

@RestController
@RequestMapping(path = {"/v1/api/moder/ps/", "/v1/api/moder/ps"})
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceService parkingSpaceService;

    @GetMapping("/search")
    public ResponseEntity<List<ParkingSpaceResponse>> getAllParkingSpace() {
        LOGGER.info("Request receive to list all the parking spaces. - ParkingSpaceController");
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpaceService.findAll());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ParkingSpaceResponse> getParkingSpaceById(@PathVariable Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to list a parking space by param id '{}' - ParkingSpaceController", id);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpaceService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteParkingSpace(@PathVariable Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to delete the Parking Space from id: '{}' - ParkingSpaceController", id);
        parkingSpaceService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("The parking space was deleted successfully.");
    }

    @PostMapping
    public ResponseEntity<ParkingSpaceResponse> createParkingSpace(
            @RequestBody @Valid ParkingSpaceRequestPost request, @PathVariable Long userId)
            throws BadRequestException {
        LOGGER.info("Saving your parking space at the system - ParkingSpaceController");

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpaceService.createParkingSpace(request, userId));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ParkingSpacePutRequest> updateParkingSpace(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid ParkingSpacePutRequest parkingSpacePutRequestUpdated) throws ParkingSpaceNotFoundException {

        LOGGER.info("Request receive to update - ParkingSpaceController");
        parkingSpaceService.update(parkingSpacePutRequestUpdated, id);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpacePutRequestUpdated);
    }
}
