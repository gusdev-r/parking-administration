package com.parking.administration.demo.service;

import com.parking.administration.demo.domain.Vehicle;
import com.parking.administration.demo.infra.exception.VehicleNotFoundException;
import com.parking.administration.demo.infra.exception.enums.ErrorCode;
import com.parking.administration.demo.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.parking.administration.demo.utils.Utility.LOGGER;

@Service
public class VehicleService {
    private VehicleRepository vehicleRepository;
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    public VehicleService() {
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }
    public Vehicle findById(Long id) {
        LOGGER.info("Searching the user by Id - ClientService");
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(ErrorCode.WA0002.getMessage(), ErrorCode.WA0002.getCode()));
    }

    public Vehicle findByLicensePlateNumber(String licensePlateNumber) {
        return vehicleRepository.findByLicensePlateNumber(licensePlateNumber);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    private void validateVehicleById(Long id) {
        LOGGER.info("Starting the validation of the vehicle space searched by Id - VehicleService");
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isEmpty()) {
            LOGGER.error("This vehicle was not found or not exists at the system - VehicleService");
            throw new VehicleNotFoundException(ErrorCode.WA0002.getMessage(), ErrorCode.WA0002.getCode());
        }
    }

}
