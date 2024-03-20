package com.parking.administration.demo.customValidator;

import com.parking.administration.demo.domain.ParkingSpace;
import com.parking.administration.demo.infra.exception.BadRequestException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import com.parking.administration.demo.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Component;

import static com.parking.administration.demo.utils.Utility.LOGGER;

@Component
public class ParkingSpaceValidator {
    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingSpaceValidator(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    public void validateRegisterToCreate(ParkingSpace parkingSpace) throws BadRequestException {
        LOGGER.info("Starting the validation if already exists vehicle license plate number. - ParkingSpaceValidator");
        if (parkingSpaceRepository.existsByVehicleLicensePlateNumber(parkingSpace.getVehicleLicensePlateNumber())) {
            LOGGER.error("This vehicle license plate number is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }
        LOGGER.info("Starting the validation if already exists condominium apartment. - ParkingSpaceValidator");
        if (parkingSpaceRepository.existsByCondominiumApartment(parkingSpace.getCondominiumApartment())) {
            LOGGER.error("This condominium apartment is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }
        LOGGER.info("Starting the validation if already exists condominium block. - ParkingSpaceValidator");
        if (parkingSpaceRepository.existsByCondominiumBlock(parkingSpace.getCondominiumBlock())) {
            LOGGER.error("This condominium block is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }
    }
}
