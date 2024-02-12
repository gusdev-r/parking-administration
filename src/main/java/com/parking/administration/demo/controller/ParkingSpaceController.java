package com.parking.administration.demo.controller;

import com.parking.administration.demo.customValidator.PSValidator;
import com.parking.administration.demo.domain.ParkingSpace;
import com.parking.administration.demo.mapper.PSMapper;
import com.parking.administration.demo.request.PSPostRequest;
import com.parking.administration.demo.request.PSPutRequest;
import com.parking.administration.demo.response.PSGetResponse;
import com.parking.administration.demo.service.ParkingSpaceService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;


@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600) // can be accessed from any place
@RequestMapping(path = "/v1/parking-space")
public class ParkingSpaceController {

    private PSMapper psMapper;
    private final PSValidator psValidator;
    private final ParkingSpaceService parkingSpaceService;

    public ParkingSpaceController(PSValidator psValidator, ParkingSpaceService parkingSpaceService) {
        this.psValidator = psValidator;
        this.parkingSpaceService = parkingSpaceService;
    }
    @GetMapping("/show-all-map")
    public ResponseEntity<Page<ParkingSpace>> getAllParkingSpaceWithMapper(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("Request receive to list all the parking spaces.");
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpaceService.findAll(pageable));
    }

    @GetMapping(path = "/show/{id}")
    public ResponseEntity<PSGetResponse> getParkingSpaceById(@PathVariable UUID id) {
        log.info("Request receive to list all the parking spaces.");
        var parkingSpace = psValidator.validateParkingSpaceById(id);
        var response = psMapper.ObjectToPSGetResponse(parkingSpace);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteParkingSpaceById(@PathVariable UUID id) {
        log.info("Request receive to delete the Parking Space from id: '{}'", id);
        psValidator.validateDeleteParkingSpaceById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Parking Space was deleted successfully.");
    }

    @PostMapping
    public ResponseEntity<Object> AddParkingSpace(@RequestBody @Valid PSPostRequest parkingSpacePostRequest) {
        psValidator.validateRegister(parkingSpacePostRequest);
        var parkingSpace = new ParkingSpace(); // model of the creation of the parking space
        BeanUtils.copyProperties(parkingSpacePostRequest, parkingSpace); // transforming the pSPR in a pS
        parkingSpace.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        log.info("Saving your parking space at the system.");
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpaceService.save(parkingSpace));
    }

    @PutMapping(path = "/save/{id}")
    public ResponseEntity<Object> updateParkingSpace(@PathVariable(value = "id") UUID id,
                                                     @RequestBody @Valid PSPutRequest psPutRequest) {
        log.info("Request receive to update the Parking Space from id: '{}'", id);
        psValidator.validateUpdateParkingSpace(id);
        var parkingSpace = new ParkingSpace();
        BeanUtils.copyProperties(psPutRequest, parkingSpace);
        parkingSpace.setId(psPutRequest.getId());
        parkingSpace.setRegistrationDate(psPutRequest.getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpaceService.save(parkingSpace));
    }
}
