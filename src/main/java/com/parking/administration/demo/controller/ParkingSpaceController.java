package com.parking.administration.demo.controller;

import com.parking.administration.demo.infra.exception.BadRequestException;
import com.parking.administration.demo.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.demo.mapper.PSMapper;
import com.parking.administration.demo.service.ParkingSpaceService;
import com.parking.administration.dto.request.ParkingSpacePutRequest;
import com.parking.administration.dto.request.ParkingSpaceRequestPost;
import com.parking.administration.dto.response.ParkingSpaceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.parking.administration.demo.utils.Utility.LOGGER;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = {"/v1/parking-space/api", "/v1/parking-space/api/"})
public class ParkingSpaceController {
    private final PSMapper psMapper;
    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceController(PSMapper psMapper, ParkingSpaceService parkingSpaceService) {
        this.psMapper = psMapper;
        this.parkingSpaceService = parkingSpaceService;
    }

    @GetMapping("/show")
    public ResponseEntity<List<ParkingSpaceResponse>> getAllParkingSpace() {
        LOGGER.info("Request receive to list all the parking spaces. - ParkingSpaceController");
        var list = parkingSpaceService.findAll();
        var listConvertedMapper = psMapper.toListParkingSpace(list);
        return ResponseEntity.status(HttpStatus.OK).body(listConvertedMapper);
    }
    @GetMapping("/show/{id}")
    public ResponseEntity<ParkingSpaceResponse> getParkingSpaceById(@PathVariable Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to list a parking space by param id '{}' - ParkingSpaceController", id);
        return ResponseEntity.status(HttpStatus.OK).body(psMapper.toOptionalParkingSpace(parkingSpaceService.findById(id).get()));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteParkingSpace(@PathVariable Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to delete the Parking Space from id: '{}' - ParkingSpaceController", id);
        var parkingSpaceOptionalToDelete = parkingSpaceService.findById(id);
        parkingSpaceService.delete(parkingSpaceOptionalToDelete.get());

        return ResponseEntity.status(HttpStatus.OK).body("The parking space was deleted successfully.");

    }
    @PostMapping("/create")
    public ResponseEntity<ParkingSpaceResponse> createParkingSpace(@RequestBody @Valid ParkingSpaceRequestPost parkingSpaceRequestPost) throws BadRequestException {
        parkingSpaceService.save(psMapper.toParkingSpaceRequest(parkingSpaceRequestPost));
        LOGGER.info("Saving your parking space at the system - ParkingSpaceController");
        var response = psMapper.toParkingSpacePostRequest(parkingSpaceRequestPost);
        //return ResponseEntity.status(HttpStatus.CREATED).body("Parking space was created with successful. - ParkingSpaceController");

        return ResponseEntity.status(HttpStatus.CREATED).body(response)  ;

    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ParkingSpacePutRequest> updateParkingSpace(@PathVariable(value = "id") Long id, @RequestBody @Valid ParkingSpacePutRequest parkingSpacePutRequestUpdated) throws ParkingSpaceNotFoundException {
        LOGGER.info("Request receive to update - ParkingSpaceController");
        parkingSpaceService.update(parkingSpacePutRequestUpdated, id);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpacePutRequestUpdated);
    }
}
