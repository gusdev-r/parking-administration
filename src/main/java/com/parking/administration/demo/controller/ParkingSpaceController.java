package com.parking.administration.demo.controller;

import com.parking.administration.demo.dto.request.ParkingSpacePutRequest;
import com.parking.administration.demo.dto.request.ParkingSpaceRequestPost;
import com.parking.administration.demo.dto.response.ParkingSpaceResponse;
import com.parking.administration.demo.infra.exception.BadRequestException;
import com.parking.administration.demo.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.demo.mapper.PSMapper;
import com.parking.administration.demo.service.ParkingSpaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parking.administration.demo.utils.Utility.LOGGER;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = {"/v1/api/moder/ps/", "/v1/api/moder/ps"})
public class ParkingSpaceController {
    private PSMapper psMapper;
    private ParkingSpaceService parkingSpaceService;

    public ParkingSpaceController( PSMapper psMapper, ParkingSpaceService parkingSpaceService) {
        this.psMapper = psMapper;
        this.parkingSpaceService = parkingSpaceService;
    }

    public ParkingSpaceController() {
    }

    @GetMapping("/search")
    public ResponseEntity<List<ParkingSpaceResponse>> getAllParkingSpace() {
        LOGGER.info("Request receive to list all the parking spaces. - ParkingSpaceController");
        var list = parkingSpaceService.findAll();
        var listConvertedMapper = psMapper.toListParkingSpace(list);
        return ResponseEntity.status(HttpStatus.OK).body(listConvertedMapper);
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<ParkingSpaceResponse> getParkingSpaceById(@PathVariable Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to list a parking space by param id '{}' - ParkingSpaceController", id);
        return ResponseEntity.status(HttpStatus.OK).body(psMapper.toOptionalParkingSpace(parkingSpaceService.findById(id).get()));
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

        parkingSpaceService.save(psMapper.toParkingSpaceRequest(request), userId);
        LOGGER.info("Saving your parking space at the system - ParkingSpaceController");

        var response = psMapper.toParkingSpacePostRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ParkingSpacePutRequest> updateParkingSpace(@PathVariable(value = "id") Long id, @RequestBody @Valid ParkingSpacePutRequest parkingSpacePutRequestUpdated) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to update - ParkingSpaceController");
        parkingSpaceService.update(parkingSpacePutRequestUpdated, id);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpacePutRequestUpdated);
    }
}
