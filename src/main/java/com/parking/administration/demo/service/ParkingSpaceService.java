package com.parking.administration.demo.service;

import com.parking.administration.demo.customValidator.ParkingSpaceValidator;
import com.parking.administration.demo.domain.ParkingSpace;
import com.parking.administration.demo.domain.Vehicle;
import com.parking.administration.demo.infra.exception.BadRequestException;
import com.parking.administration.demo.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import com.parking.administration.demo.repository.ParkingSpaceRepository;
import com.parking.administration.dto.request.ParkingSpacePutRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.parking.administration.demo.utils.Utility.LOGGER;

@Service
public class ParkingSpaceService {
    private ParkingSpaceRepository psRepository;
    private ParkingSpaceValidator parkingSpaceValidator;
    private VehicleService vehicleService;

    @Autowired
    public ParkingSpaceService(ParkingSpaceRepository psRepository, ParkingSpaceValidator parkingSpaceValidator, VehicleService vehicleService) {
        this.psRepository = psRepository;
        this.parkingSpaceValidator = parkingSpaceValidator;
        this.vehicleService = vehicleService;
    }

    public ParkingSpaceService() {

    }

    @Transactional
    public ParkingSpace save(ParkingSpace parkingSpace) throws BadRequestException {
        parkingSpaceValidator.validateRegisterToCreate(parkingSpace);
        return psRepository.save(parkingSpace);

    }
    @Transactional
    public void create(ParkingSpace parkingSpace) {
        parkingSpaceValidator.validateRegisterToCreate(parkingSpace);
        Vehicle vehicle = new Vehicle(
                        parkingSpace.getVehicleBrand(),
                        parkingSpace.getVehicleColor(),
                        parkingSpace.getVehicleModel(),
                        parkingSpace.getVehicleLicensePlateNumber());
        vehicleService.save(vehicle);
        psRepository.save(parkingSpace);
    }

    public boolean existsByVehicleLicensePlateNumber(String vehicleSpaceNumber) {
        return psRepository.existsByVehicleLicensePlateNumber(vehicleSpaceNumber);
    }
    public boolean existsByCondominiumApartment (String apartment) {
        return psRepository.existsByCondominiumApartment(apartment);
    }
    public boolean existsByCondominiumBlock (String block) {
        return psRepository.existsByCondominiumBlock(block);
    }
    public List<ParkingSpace> findAll() {
        return psRepository.findAll();
    }
    public Optional<ParkingSpace> findById(Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Validating the search - ParkingSpaceService");
        validateParkingSpaceById(id);
        return psRepository.findById(id);
    }
    @Transactional
    public void delete(ParkingSpace parkingSpace) {
        LOGGER.info("Validating the delete - ParkingSpaceService");
        psRepository.delete(parkingSpace);
    }
    public void update(ParkingSpacePutRequest parkingSpacePutRequestUpdated, Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Validating the update - ParkingSpaceService");
        var optionalParkingSpaceToUpdate = psRepository.findById(id);
        validateParkingSpaceById(id);

        LOGGER.info("The parking space is updating - ParkingSpaceService");
        var parkingSpace = new ParkingSpace();
        BeanUtils.copyProperties(parkingSpacePutRequestUpdated, parkingSpace);
        parkingSpace.setId(optionalParkingSpaceToUpdate.get().getId());
        parkingSpace.setCreatedAt(optionalParkingSpaceToUpdate.get().getCreatedAt());
        psRepository.save(parkingSpace);
    }
    private void validateParkingSpaceById(Long id) {
        LOGGER.info("Starting the validation of the parking space searched by Id - ParkingSpaceValidator");
        Optional<ParkingSpace> psOptional = psRepository.findById(id);
        if (psOptional.isEmpty()) {
            LOGGER.error("This parking space was not found or not exists at the system - ParkingSpaceValidator");
            throw new ParkingSpaceNotFoundException(ErrorCode.WA0001.getMessage(), ErrorCode.WA0001.getCode());
        }
    }
}
