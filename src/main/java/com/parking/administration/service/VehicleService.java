package com.parking.administration.service;

import com.parking.administration.domain.Vehicle;
import com.parking.administration.dto.response.VehicleResponse;
import com.parking.administration.infra.exception.VehicleNotFoundException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import com.parking.administration.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.parking.administration.util.Utility.LOGGER;

@RequiredArgsConstructor
@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public List<VehicleResponse> findAll() {
        return vehicleRepository.findAll().stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleResponse.class)).toList();
    }
    public VehicleResponse findById(Long id) {
        LOGGER.info("Searching the user by Id - ClientService");
        Vehicle vehicle =  vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(ErrorCode.WA0002.getMessage(), ErrorCode.WA0002.getCode()));
        return modelMapper.map(vehicle, VehicleResponse.class);
    }

    public VehicleResponse findByLicensePlateNumber(String licensePlateNumber) {
        Vehicle vehicle = vehicleRepository.findByLicensePlateNumber(licensePlateNumber);
        return modelMapper.map(vehicle, VehicleResponse.class);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
}
