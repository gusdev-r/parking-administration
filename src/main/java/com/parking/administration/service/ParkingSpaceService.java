package com.parking.administration.service;

import com.parking.administration.domain.ParkingSpace;
import com.parking.administration.domain.User;
import com.parking.administration.domain.Vehicle;
import com.parking.administration.dto.request.ParkingSpacePutRequest;
import com.parking.administration.infra.exception.BadRequestException;
import com.parking.administration.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.infra.exception.UserNotFoundException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import com.parking.administration.repository.ParkingSpaceRepository;
import com.parking.administration.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.parking.administration.util.Utility.LOGGER;

@RequiredArgsConstructor
@Service
public class ParkingSpaceService {
    private final ParkingSpaceRepository psRepository;
    private final VehicleService vehicleService;
    private final UserRepository userRepository;

    @Transactional
    public ParkingSpace save(ParkingSpace parkingSpace, Long userId) throws BadRequestException {

        LOGGER.info("Validating if already exists vehicle license plate number. - ParkingSpaceValidator");
        if (psRepository.existsByVehicleLicensePlateNumber(parkingSpace.getVehicleLicensePlateNumber())) {
            LOGGER.error("This vehicle license plate number is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }

        LOGGER.info("Validating validation if already exists condominium apartment. - ParkingSpaceValidator");
        if (psRepository.existsByCondominiumApartment(parkingSpace.getCondominiumApartment())) {
            LOGGER.error("This condominium apartment is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }

        LOGGER.info("Validating if already exists condominium block. - ParkingSpaceValidator");
        if (psRepository.existsByCondominiumBlock(parkingSpace.getCondominiumBlock())) {
            LOGGER.error("This condominium block is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            LOGGER.warn("User not found - ParkingSpaceService");
            throw new UserNotFoundException(ErrorCode.WA0003.getMessage(), ErrorCode.WA0003.getCode());
        }
        Vehicle vehicle = new Vehicle(
                parkingSpace.getVehicleBrand(),
                parkingSpace.getVehicleModel(),
                parkingSpace.getVehicleColor(),
                parkingSpace.getVehicleLicensePlateNumber());
        vehicleService.save(vehicle);

        userOptional.get().getVehicleList().add(vehicle);
        userOptional.get().getParkingSpaceList().add(parkingSpace);

        return psRepository.save(parkingSpace);
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
    public void update(ParkingSpacePutRequest parkingSpaceRequestUpdated, Long id) throws ParkingSpaceNotFoundException {
        LOGGER.info("Validating the update - ParkingSpaceService");
        var optionalParkingSpaceToUpdate = psRepository.findById(id);
        validateParkingSpaceById(id);

        LOGGER.info("The parking space is updating - ParkingSpaceService");
        var parkingSpace = new ParkingSpace();
        BeanUtils.copyProperties(parkingSpaceRequestUpdated, parkingSpace);
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
