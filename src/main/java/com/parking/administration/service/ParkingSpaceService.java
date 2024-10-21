package com.parking.administration.service;

import com.parking.administration.domain.core.ParkingSpace;
import com.parking.administration.domain.core.User;
import com.parking.administration.domain.core.Vehicle;
import com.parking.administration.dto.request.ParkingSpacePutRequest;
import com.parking.administration.dto.request.ParkingSpaceRequestPost;
import com.parking.administration.dto.response.ParkingSpaceResponse;
import com.parking.administration.infra.exception.BadRequestException;
import com.parking.administration.infra.exception.ParkingSpaceNotFoundException;
import com.parking.administration.infra.exception.UserNotFoundException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import com.parking.administration.repository.ParkingSpaceRepository;
import com.parking.administration.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Transactional
    public ParkingSpaceResponse createParkingSpace(ParkingSpaceRequestPost parkingSpace, Long userId) throws BadRequestException {

        LOGGER.info("Validating if already exists vehicle license plate number. - ParkingSpaceValidator");
        if (psRepository.existsByVehicleLicensePlateNumber(parkingSpace.vehicleLicensePlateNumber())) {
            LOGGER.error("This vehicle license plate number is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }

        LOGGER.info("Validating validation if already exists condominium apartment. - ParkingSpaceValidator");
        if (psRepository.existsByCondominiumApartment(parkingSpace.condominiumApartment())) {
            LOGGER.error("This condominium apartment is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }

        LOGGER.info("Validating if already exists condominium block. - ParkingSpaceValidator");
        if (psRepository.existsByCondominiumBlock(parkingSpace.condominiumBlock())) {
            LOGGER.error("This condominium block is already registered - ParkingSpaceValidator");
            throw new BadRequestException(ErrorCode.ON0001.getMessage(), ErrorCode.ON0001.getCode());
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.WA0003.getMessage(), ErrorCode.WA0003.getCode()));

        Vehicle vehicleToSave = new Vehicle(
                parkingSpace.vehicleBrand(),
                parkingSpace.vehicleModel(),
                parkingSpace.vehicleColor(),
                parkingSpace.vehicleLicensePlateNumber());
        vehicleService.save(vehicleToSave);

        ParkingSpace parkingSpaceToSave = ParkingSpace.builder()
                .condominiumBlock(parkingSpace.condominiumBlock())
                .condominiumApartment(parkingSpace.condominiumApartment())
                .vehicleBrand(parkingSpace.vehicleBrand())
                .vehicleModel(parkingSpace.vehicleModel())
                .vehicleColor(parkingSpace.vehicleColor())
                .responsibleVehicleName(parkingSpace.responsibleVehicleName())
                .vehicleSpaceNumber(parkingSpace.vehicleSpaceNumber())
                .vehicleLicensePlateNumber(parkingSpace.vehicleLicensePlateNumber())
                .build();

        user.getVehicleList().add(vehicleToSave);
        user.getParkingSpaceList().add(parkingSpaceToSave);

        ParkingSpace parkingSpaceSaved = psRepository.save(parkingSpaceToSave);
        return modelMapper.map(parkingSpaceSaved, ParkingSpaceResponse.class);
    }

    public List<ParkingSpaceResponse> findAll() {
        return psRepository.findAll().stream().map(parkingSpace -> modelMapper
                .map(parkingSpace, ParkingSpaceResponse.class)).toList();
    }
    public ParkingSpaceResponse findById(Long id) throws ParkingSpaceNotFoundException {
        validateParkingSpaceById(id);
        ParkingSpace parkingSpace = psRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("ParkingSpace not found", ErrorCode.WA0003.getCode()));
        return modelMapper.map(parkingSpace, ParkingSpaceResponse.class);
    }
    @Transactional
    public void delete(Long id) {
        validateParkingSpaceById(id);
        ParkingSpace parkingSpace = psRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("ParkingSpace not found", ErrorCode.WA0003.getCode()));

        LOGGER.info("Validating the delete - ParkingSpaceService");
        psRepository.delete(parkingSpace);
    }
    public void update(ParkingSpacePutRequest parkingSpaceRequestUpdated, Long id) throws ParkingSpaceNotFoundException {
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
        Optional<ParkingSpace> psOptional = psRepository.findById(id);
        if (psOptional.isEmpty()) {
            LOGGER.error("This parking space was not found or not exists at the system - ParkingSpaceValidator");
            throw new ParkingSpaceNotFoundException(ErrorCode.WA0001.getMessage(), ErrorCode.WA0001.getCode());
        }
    }
}
