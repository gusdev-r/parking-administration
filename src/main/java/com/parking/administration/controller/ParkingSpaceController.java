package com.parking.administration.controller;

import com.parking.administration.dto.request.ParkingSpacePutRequest;
import com.parking.administration.dto.request.ParkingSpaceRequestPost;
import com.parking.administration.dto.response.ParkingSpaceResponse;
import com.parking.administration.infra.exception.BadRequestException;
import com.parking.administration.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.mapper.ParkingSpaceMapper;
import com.parking.administration.service.ParkingSpaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parking.administration.util.Utility.LOGGER;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = {"/v1/api/moder/ps/", "/v1/api/moder/ps"})
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final ParkingSpaceMapper mapper;
    private final ParkingSpaceService parkingSpaceService;

    @GetMapping("/search")
    public ResponseEntity<List<ParkingSpaceResponse>> getAllParkingSpace() {
        LOGGER.info("Request receive to list all the parking spaces. - ParkingSpaceController");
        var list = parkingSpaceService.findAll();
        var listConvertedMapper = mapper.toListParkingSpace(list);
        return ResponseEntity.status(HttpStatus.OK).body(listConvertedMapper);
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<ParkingSpaceResponse> getParkingSpaceById(@PathVariable Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to list a parking space by param id '{}' - ParkingSpaceController", id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toOptionalParkingSpace(parkingSpaceService.findById(id).get()));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteParkingSpace(@PathVariable Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to delete the Parking Space from id: '{}' - ParkingSpaceController", id);
        var parkingSpaceOptionalToDelete = parkingSpaceService.findById(id);
        parkingSpaceService.delete(parkingSpaceOptionalToDelete.get());

        return ResponseEntity.status(HttpStatus.OK).body("The parking space was deleted successfully.");

    }
    @PostMapping
    public ResponseEntity<ParkingSpaceResponse> createParkingSpace(
            @RequestBody @Valid ParkingSpaceRequestPost request, @PathVariable Long userId)
            throws BadRequestException {

        parkingSpaceService.save(mapper.toParkingSpaceRequest(request), userId);
        LOGGER.info("Saving your parking space at the system - ParkingSpaceController");

        var response = mapper.toParkingSpacePostRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ParkingSpacePutRequest> updateParkingSpace(@PathVariable(value = "id") Long id, @RequestBody @Valid ParkingSpacePutRequest parkingSpacePutRequestUpdated) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to update - ParkingSpaceController");
        parkingSpaceService.update(parkingSpacePutRequestUpdated, id);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpacePutRequestUpdated);
    }
}
